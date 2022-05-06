package com.ramiyon.soulmath.domain.usecase.login

import com.ramiyon.soulmath.util.Resource
import kotlinx.coroutines.flow.Flow

interface LoginUseCase {
    suspend fun savePrefRememberMe(isRemember: Boolean)
    fun signIn(email: String, password: String): Flow<Resource<Unit>>
}