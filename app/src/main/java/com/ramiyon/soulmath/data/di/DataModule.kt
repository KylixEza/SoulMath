package com.ramiyon.soulmath.data.di

import com.ramiyon.soulmath.data.source.local.LocalDataSource
import com.ramiyon.soulmath.data.source.local.datastore.SoulMathDataStore
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataStoreModule = module {
    single { SoulMathDataStore(androidApplication()) }
}

val localDataSourceModule = module {
    single { LocalDataSource(get()) }
}