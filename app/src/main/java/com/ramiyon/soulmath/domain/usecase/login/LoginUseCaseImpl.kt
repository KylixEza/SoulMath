package com.ramiyon.soulmath.domain.usecase.login

import com.ramiyon.soulmath.domain.repository.SoulMathRepository
import kotlinx.coroutines.flow.Flow

class LoginUseCaseImpl(
    private val repository: SoulMathRepository
): LoginUseCase {
    override suspend fun savePrefRememberMe(isRemember: Boolean) = repository.savePrefRememberMe(isRemember)
    override suspend fun savePrefHaveLoginAppBefore(isLogin: Boolean) = repository.savePrefHaveLoginAppBefore(isLogin)
    override suspend fun savePrefHaveRunAppBefore(isFirstTime: Boolean) = repository.savePrefHaveRunAppBefore(isFirstTime)
}