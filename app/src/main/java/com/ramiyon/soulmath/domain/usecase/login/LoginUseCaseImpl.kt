package com.ramiyon.soulmath.domain.usecase.login

import com.ramiyon.soulmath.domain.repository.SoulMathRepository
import com.ramiyon.soulmath.util.Resource
import kotlinx.coroutines.flow.Flow

class LoginUseCaseImpl(
    private val repository: SoulMathRepository
): LoginUseCase {
    override suspend fun savePrefRememberMe(isRemember: Boolean) = repository.savePrefRememberMe(isRemember)
    override fun signIn(email: String, password: String): Flow<Resource<Unit>> = repository.signIn(email, password)

}