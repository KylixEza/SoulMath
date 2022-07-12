package com.ramiyon.soulmath.presentation.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ramiyon.soulmath.domain.usecase.profile.ProfileUseCase

class ProfileViewModel(
    private val useCase: ProfileUseCase
) : ViewModel() {

    fun getStudentDetail() = useCase.getStudentDetail().asLiveData()

}