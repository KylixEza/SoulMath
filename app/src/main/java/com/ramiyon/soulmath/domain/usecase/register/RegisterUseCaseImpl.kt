package com.ramiyon.soulmath.domain.usecase.register

import com.ramiyon.soulmath.data.source.remote.api.response.student.StudentBody
import com.ramiyon.soulmath.domain.repository.SoulMathRepository

class RegisterUseCaseImpl(
    private val repository: SoulMathRepository
): RegisterUseCase {

    override fun signUp(email: String, password: String, body: StudentBody) =
        repository.signUp(email, password, body)

    override suspend fun savePrefHaveRunAppBefore(isFirstTime: Boolean) = repository.savePrefHaveRunAppBefore(isFirstTime)
}