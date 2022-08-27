package com.ramiyon.soulmath.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ramiyon.soulmath.R
import com.ramiyon.soulmath.data.connectivity.NetworkConnectivityObserver
import com.ramiyon.soulmath.data.repository.SoulMathRepositoryImpl
import com.ramiyon.soulmath.data.source.local.LocalDataSource
import com.ramiyon.soulmath.data.source.local.database.enitity.DailyXpEntity
import com.ramiyon.soulmath.data.source.local.database.room.SoulMathDao
import com.ramiyon.soulmath.data.source.local.database.room.SoulMathDatabase
import com.ramiyon.soulmath.data.source.local.datastore.SoulMathDataStore
import com.ramiyon.soulmath.data.source.remote.RemoteDataSource
import com.ramiyon.soulmath.data.source.remote.api.ApiService
import com.ramiyon.soulmath.data.source.remote.firebase.FirebaseService
import com.ramiyon.soulmath.domain.repository.SoulMathRepository
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

val dataStoreModule = module {
    single { SoulMathDataStore(androidApplication()) }
}

val networkModule = module {
    single {
        val hostname = ""
        OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api-ramiyon-soulmath-test.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val databaseModule = module {
    factory {
        get<SoulMathDatabase>().soulMathDao()
    }
    single {

        fun loadJsonArray(context: Context): JSONArray? {
            val builder = StringBuilder()
            val `in` = context.resources.openRawResource(R.raw.daily_xp)
            val reader = BufferedReader(InputStreamReader(`in`))
            var line: String?
            try {
                while (reader.readLine().also { line = it } != null) {
                    builder.append(line)
                }
                val json = JSONObject(builder.toString())
                return json.getJSONArray("dailyXp")
            } catch (exception: IOException) {
                exception.printStackTrace()
            } catch (exception: JSONException) {
                exception.printStackTrace()
            }
            return null
        }

        fun fillWithStartingData(context: Context, dao: SoulMathDao) {
            val task = loadJsonArray(context)
            try {
                if (task != null) {
                    for (i in 0 until task.length()) {
                        val item = task.getJSONObject(i)
                        dao.insertAllDailyXp(
                            DailyXpEntity(
                                item.getString("dailyXpId"),
                                item.getInt("dailyXp"),
                                item.getInt("day"),
                                item.getString("dayTaken"),
                                item.getBoolean("isTaken")
                            )
                        )
                    }
                }
            } catch (exception: JSONException) {
                exception.printStackTrace()
            }
        }

        Room.databaseBuilder(
            androidContext(),
            SoulMathDatabase::class.java, "soulmath.db"
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Executors.newSingleThreadScheduledExecutor().execute {
                    fillWithStartingData(
                        androidApplication(),
                        get()
                    )
                }
            }
        }).fallbackToDestructiveMigration()
            .build()
    }
}

val firebaseModule = module {
    single { FirebaseService() }
}

val repositoryModule = module {
    single { LocalDataSource(get(), get()) }
    single { RemoteDataSource(get(), get()) }
    single<SoulMathRepository> {
        SoulMathRepositoryImpl(androidApplication(), get(), get())
    }
}

val networkConnectivityModule = module {
    single { NetworkConnectivityObserver(androidApplication()) }
}