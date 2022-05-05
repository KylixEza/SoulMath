package com.ramiyon.soulmath.domain.usecase.register

import com.ramiyon.soulmath.data.source.remote.api.response.UserBody
import com.ramiyon.soulmath.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface RegisterUseCase {

    fun signUp(email: String, password: String, body: UserBody): Flow<Resource<Unit>>
    suspend fun savePrefHaveLoginAppBefore(isLogin: Boolean)
    suspend fun savePrefHaveRunAppBefore(isFirstTime: Boolean)
}