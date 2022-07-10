package com.ramiyon.soulmath.data.di

import androidx.room.Room
import com.ramiyon.soulmath.data.repository.SoulMathRepositoryImpl
import com.ramiyon.soulmath.data.source.local.LocalDataSource
import com.ramiyon.soulmath.data.source.local.database.room.SoulMathDatabase
import com.ramiyon.soulmath.data.source.local.datastore.SoulMathDataStore
import com.ramiyon.soulmath.data.source.remote.RemoteDataSource
import com.ramiyon.soulmath.data.source.remote.api.ApiService
import com.ramiyon.soulmath.data.source.remote.firebase.FirebaseService
import com.ramiyon.soulmath.domain.repository.SoulMathRepository
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
            .baseUrl("http://192.168.43.15:8080")
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
        Room.databaseBuilder(
            androidContext(),
            SoulMathDatabase::class.java, "soulmath.db"
        ).fallbackToDestructiveMigration()
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