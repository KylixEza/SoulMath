package com.ramiyon.soulmath.presentation.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ramiyon.soulmath.domain.usecase.login.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase): ViewModel() {
    fun savePrefRememberMe(isRemember: Boolean) = viewModelScope.launch {
        loginUseCase.savePrefRememberMe(isRemember)
    }

    fun savePrefHaveLoginAppBefore(isLogin: Boolean) = viewModelScope.launch {
        loginUseCase.savePrefHaveLoginAppBefore(isLogin)
    }

    fun signIn(email: String, password: String) = loginUseCase.signIn(email, password).asLiveData()
}