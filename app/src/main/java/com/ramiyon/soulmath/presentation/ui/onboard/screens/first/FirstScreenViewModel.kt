package com.ramiyon.soulmath.presentation.ui.onboard.screens.first

import androidx.lifecycle.ViewModel
import com.ramiyon.soulmath.domain.usecase.onboard.OnBoardUseCase

class FirstScreenViewModel(
    private val useCase: OnBoardUseCase
): ViewModel() {

    val title = useCase.getOnBoardTitle(0)
    val subtitle = useCase.getOnBoardSubtitle(0)

}