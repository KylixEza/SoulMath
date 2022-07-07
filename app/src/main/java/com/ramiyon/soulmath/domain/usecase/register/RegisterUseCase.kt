package com.ramiyon.soulmath.domain.usecase.register

import com.ramiyon.soulmath.domain.model.Student
import com.ramiyon.soulmath.util.Resource
import kotlinx.coroutines.flow.Flow

interface RegisterUseCase {

    fun signUp(email: String, password: String, student: Student): Flow<Resource<Unit>>
    suspend fun savePrefHaveRunAppBefore(isFirstTime: Boolean)
}