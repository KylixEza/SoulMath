package com.ramiyon.soulmath.domain.usecase.register

import com.ramiyon.soulmath.data.source.remote.api.response.UserBody
import com.ramiyon.soulmath.domain.repository.SoulMathRepository

class RegisterUseCaseImpl(
    private val repository: SoulMathRepository
): RegisterUseCase {

    override fun signUp(email: String, password: String, body: UserBody) =
        repository.signUp(email, password, body)

    override suspend fun savePrefHaveLoginAppBefore(isLogin: Boolean) = repository.savePrefHaveLoginAppBefore(isLogin)
    override suspend fun savePrefHaveRunAppBefore(isFirstTime: Boolean) = repository.savePrefHaveRunAppBefore(isFirstTime)
}