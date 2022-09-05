package com.ramiyon.soulmath.domain.usecase.splash

import com.ramiyon.soulmath.domain.repository.SoulMathRepository

class SplashUseCaseImpl(
    private val repository: SoulMathRepository
): SplashUseCase {

    override fun readPrefRememberMe() = repository.readPrefRememberMe()
    override fun readPrefHaveRunAppBefore() = repository.readPrefHaveRunAppBefore()
    override fun fetchStudentDetail() = repository.fetchStudentDetail()
}