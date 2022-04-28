package com.ramiyon.soulmath.domain.usecase.login

interface LoginUseCase {
    suspend fun savePrefRememberMe(isRemember: Boolean)
    suspend fun savePrefHaveLoginAppBefore(isLogin: Boolean)
    suspend fun savePrefHaveRunAppBefore(isFirstTime: Boolean)
}