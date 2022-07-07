package com.ramiyon.soulmath.presentation.ui.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ramiyon.soulmath.domain.model.Student
import com.ramiyon.soulmath.domain.usecase.register.RegisterUseCase
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    fun signUp(email: String, password: String, student: Student) =
        registerUseCase.signUp(email, password, student).asLiveData()

    fun savePrefHaveRunAppBefore(isFirstTime: Boolean) = viewModelScope.launch {
        registerUseCase.savePrefHaveRunAppBefore(isFirstTime)
    }

}