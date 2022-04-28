package com.ramiyon.soulmath.data.source.local

import com.ramiyon.soulmath.data.source.local.datastore.SoulMathDataStore

class LocalDataSource(
    private val dataStore: SoulMathDataStore
) {

    suspend fun savePrefRememberMe(isRemember: Boolean) = dataStore.savePrefRememberMe(isRemember)
    suspend fun savePrefHaveLoginAppBefore(isLogin: Boolean) = dataStore.savePrefHaveLoginAppBefore(isLogin)
    suspend fun savePrefHaveRunAppBefore(isFirstTime: Boolean) = dataStore.savePrefHaveRunAppBefore(isFirstTime)

    fun readPrefRememberMe() = dataStore.readPrefRememberMe()
    fun readPrefHaveLoginAppBefore() = dataStore.readPrefHaveLoginAppBefore()
    fun readPrefHaveRunAppBefore() = dataStore.readPrefHaveRunAppBefore()
}