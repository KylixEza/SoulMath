package com.ramiyon.soulmath.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ramiyon.soulmath.domain.usecase.home.HomeUseCase

class HomeViewModel(
    private val useCase: HomeUseCase
): ViewModel() {
    fun fetchLearningJourney() = useCase.fetchLearningJourney().asLiveData()
}