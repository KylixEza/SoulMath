package com.ramiyon.soulmath.domain.repository

import com.ramiyon.soulmath.data.source.remote.api.response.UserBody
import com.ramiyon.soulmath.util.Resource
import kotlinx.coroutines.flow.Flow

interface SoulMathRepository {
    suspend fun savePrefRememberMe(isRemember: Boolean)
    suspend fun savePrefHaveRunAppBefore(isFirstTime: Boolean)

    fun readPrefRememberMe(): Flow<Boolean>
    fun readPrefHaveRunAppBefore(): Flow<Boolean>

    fun signUp(email: String, password: String, body: UserBody): Flow<Resource<Unit>>
    fun signIn(email: String, password: String): Flow<Resource<Unit>>
}