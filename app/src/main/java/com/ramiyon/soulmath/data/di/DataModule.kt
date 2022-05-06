package com.ramiyon.soulmath.data.di

import com.ramiyon.soulmath.data.repository.SoulMathRepositoryImpl
import com.ramiyon.soulmath.data.source.local.LocalDataSource
import com.ramiyon.soulmath.data.source.local.datastore.SoulMathDataStore
import com.ramiyon.soulmath.data.source.remote.RemoteDataSource
import com.ramiyon.soulmath.data.source.remote.firebase.FirebaseService
import com.ramiyon.soulmath.domain.repository.SoulMathRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataStoreModule = module {
    single { SoulMathDataStore(androidApplication()) }
}

val firebaseModule = module {
    single { FirebaseService() }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single<SoulMathRepository> {
        SoulMathRepositoryImpl(get(), get())
    }
}