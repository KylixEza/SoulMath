package com.ramiyon.soulmath.domain.usecase.register

import com.ramiyon.soulmath.data.source.remote.api.response.student.StudentBody
import com.ramiyon.soulmath.domain.model.Student
import com.ramiyon.soulmath.domain.repository.SoulMathRepository

class RegisterUseCaseImpl(
    private val repository: SoulMathRepository
): RegisterUseCase {

    override fun signUp(email: String, password: String, student: Student) =
        repository.signUp(email, password, student)

    override suspend fun savePrefHaveRunAppBefore(isFirstTime: Boolean) = repository.savePrefHaveRunAppBefore(isFirstTime)
}