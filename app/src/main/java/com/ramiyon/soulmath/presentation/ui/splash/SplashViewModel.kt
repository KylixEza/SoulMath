package com.ramiyon.soulmath.presentation.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ramiyon.soulmath.domain.usecase.splash.SplashUseCase
import kotlinx.coroutines.launch

class SplashViewModel(
    private val splashUseCase: SplashUseCase
) : ViewModel() {

    fun readPrefHaveRunAppBefore() = splashUseCase.readPrefHaveRunAppBefore().asLiveData()
    fun readPrefRememberMe() = splashUseCase.readPrefRememberMe().asLiveData()
    fun fetchStudentDetail() = splashUseCase.fetchStudentDetail().asLiveData()
}