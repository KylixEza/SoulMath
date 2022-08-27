package com.ramiyon.soulmath

import android.app.Application
import com.ramiyon.soulmath.data.di.*
import com.ramiyon.soulmath.domain.di.useCaseModule
import com.ramiyon.soulmath.presentation.di.adapterModule
import com.ramiyon.soulmath.presentation.di.viewModelModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class SoulMath: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@SoulMath)
            modules(
                listOf(
                    dataStoreModule,
                    networkModule,
                    databaseModule,
                    firebaseModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule,
                    adapterModule,
                    lifecycleModule,
                    networkConnectivityModule
                )
            )
        }
    }
}