package com.ramiyon.soulmath.presentation.ui.auth.login

import androidx.lifecycle.ViewModel
import com.ramiyon.soulmath.domain.usecase.login.LoginUseCase

class LoginViewModel(private val loginUseCase: LoginUseCase): ViewModel() {
    suspend fun savePrefRememberMe(isRemember: Boolean) = loginUseCase.savePrefRememberMe(isRemember)
    suspend fun savePrefHaveLoginAppBefore(isLogin: Boolean) = loginUseCase.savePrefHaveLoginAppBefore(isLogin)
    suspend fun savePrefHaveRunAppBefore(isFirstTime: Boolean) = loginUseCase.savePrefHaveRunAppBefore(isFirstTime)
}