package com.ramiyon.soulmath.domain.repository

import kotlinx.coroutines.flow.Flow

interface SoulMathRepository {
    suspend fun savePrefRememberMe(isRemember: Boolean)
    suspend fun savePrefHaveLoginAppBefore(isLogin: Boolean)
    suspend fun savePrefHaveRunAppBefore(isFirstTime: Boolean)

    fun readPrefRememberMe(): Flow<Boolean>
    fun readPrefHaveLoginAppBefore(): Flow<Boolean>
    fun readPrefHaveRunAppBefore(): Flow<Boolean>
}