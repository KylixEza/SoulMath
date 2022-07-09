package com.ramiyon.soulmath.presentation.ui.onboard.screens.third

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramiyon.soulmath.domain.usecase.onboard.OnBoardUseCase
import kotlinx.coroutines.launch

class ThirdScreenViewModel(
    private val useCase: OnBoardUseCase
): ViewModel() {
    val title = useCase.getOnBoardTitle(2)
    val subtitle = useCase.getOnBoardSubtitle(2)

    fun savePrefHaveRunAppBefore(isFirstTime: Boolean) = viewModelScope.launch {
        useCase.savePrefHaveRunAppBefore(isFirstTime)
    }
}