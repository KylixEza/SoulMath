package com.ramiyon.soulmath.domain.usecase.splash

import kotlinx.coroutines.flow.Flow

interface SplashUseCase {

    fun readPrefRememberMe(): Flow<Boolean>
    fun readPrefHaveRunAppBefore(): Flow<Boolean>

}