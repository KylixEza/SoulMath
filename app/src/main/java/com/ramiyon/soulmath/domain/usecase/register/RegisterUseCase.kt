package com.ramiyon.soulmath.domain.usecase.register

import com.ramiyon.soulmath.data.source.remote.api.response.student.StudentBody
import com.ramiyon.soulmath.util.Resource
import kotlinx.coroutines.flow.Flow

interface RegisterUseCase {

    fun signUp(email: String, password: String, body: StudentBody): Flow<Resource<Unit>>
    suspend fun savePrefHaveRunAppBefore(isFirstTime: Boolean)
}