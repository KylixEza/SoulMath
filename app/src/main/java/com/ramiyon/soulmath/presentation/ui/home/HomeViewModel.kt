package com.ramiyon.soulmath.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ramiyon.soulmath.domain.usecase.home.HomeUseCase
import kotlinx.coroutines.Dispatchers

class HomeViewModel(
    private val useCase: HomeUseCase
): ViewModel() {
    fun fetchLearningJourney() = useCase.fetchLearningJourney().asLiveData(Dispatchers.IO)
    fun getStudentDetail() = useCase.getStudentDetail().asLiveData(Dispatchers.IO)
    fun getCurrentDailyXp() = useCase.getCurrentDailyXp().asLiveData(Dispatchers.IO)
    fun takeDailyXp(dailyXpId: String) = useCase.takeDailyXp(dailyXpId).asLiveData(Dispatchers.IO)
}