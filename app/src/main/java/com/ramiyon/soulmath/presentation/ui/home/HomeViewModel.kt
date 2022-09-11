package com.ramiyon.soulmath.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ramiyon.soulmath.domain.usecase.home.HomeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(
    private val useCase: HomeUseCase
): ViewModel() {
    
    private val _studentXp = MutableStateFlow(0)
    val studentXp = _studentXp.asStateFlow()
    
    private val _collectedText = MutableStateFlow("")
    val collectedText = _collectedText.asStateFlow()
    
    fun fetchLearningJourney() = useCase.fetchLearningJourney().asLiveData(Dispatchers.IO)
    fun getStudentDetail() = useCase.getStudentDetail().asLiveData(Dispatchers.IO)
    fun getCurrentDailyXp() = useCase.getCurrentDailyXp().asLiveData(Dispatchers.IO)
    fun takeDailyXp(dailyXpId: String) = useCase.takeDailyXp(dailyXpId).asLiveData(Dispatchers.IO)
    fun isTodayTaken() = useCase.isTodayTaken().asLiveData(Dispatchers.IO)
    fun getTodayTakenXp() = useCase.getTodayTakenXp().asLiveData(Dispatchers.Main)
    
    fun setStudentXp(xp: Int) {
        _studentXp.value = xp
    }
    
    fun increaseStudentXp(xp: Int) {
        _studentXp.value += xp
    }
    
    fun setCollectedText(text: String) {
        _collectedText.value = text
    }
}