package com.ramiyon.soulmath.domain.usecase.splash

import com.ramiyon.soulmath.util.Resource
import kotlinx.coroutines.flow.Flow

interface SplashUseCase {

    fun readPrefRememberMe(): Flow<Boolean>
    fun readPrefHaveRunAppBefore(): Flow<Boolean>
    fun fetchStudentDetail(): Flow<Resource<Unit>>
}