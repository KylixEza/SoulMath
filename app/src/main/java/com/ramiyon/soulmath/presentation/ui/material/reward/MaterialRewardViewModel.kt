package com.ramiyon.soulmath.presentation.ui.material.reward

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ramiyon.soulmath.domain.usecase.material.reward.MaterialRewardUseCase

class MaterialRewardViewModel(
    private val useCase: MaterialRewardUseCase
): ViewModel() {
    fun increaseStudentXp(givenXp: Int) = useCase.increaseStudentXp(givenXp).asLiveData()
}