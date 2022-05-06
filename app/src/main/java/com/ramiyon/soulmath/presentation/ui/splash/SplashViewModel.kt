package com.ramiyon.soulmath.presentation.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ramiyon.soulmath.domain.usecase.splash.SplashUseCase

class SplashViewModel(
    private val splashUseCase: SplashUseCase
) : ViewModel() {

    fun readPrefHaveRunAppBefore() = splashUseCase.readPrefHaveRunAppBefore().asLiveData()
    fun readPrefRememberMe() = splashUseCase.readPrefRememberMe().asLiveData()

}