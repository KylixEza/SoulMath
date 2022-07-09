package com.ramiyon.soulmath.presentation.ui.onboard.screens.second

import androidx.lifecycle.ViewModel
import com.ramiyon.soulmath.data.source.dummy.getOnBoardContentByPage
import com.ramiyon.soulmath.domain.usecase.onboard.OnBoardUseCase

class SecondScreenViewModel(
    private val useCase: OnBoardUseCase
): ViewModel() {

    val title = useCase.getOnBoardTitle(1)
    val subtitle = useCase.getOnBoardSubtitle(1)
}