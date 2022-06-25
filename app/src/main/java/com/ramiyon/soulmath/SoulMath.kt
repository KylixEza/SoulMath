package com.ramiyon.soulmath

import android.app.Application
import com.ramiyon.soulmath.data.di.dataStoreModule
import com.ramiyon.soulmath.data.di.firebaseModule
import com.ramiyon.soulmath.data.di.networkModule
import com.ramiyon.soulmath.data.di.repositoryModule
import com.ramiyon.soulmath.domain.di.useCaseModule
import com.ramiyon.soulmath.presentation.di.viewModelModule
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
                    firebaseModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}