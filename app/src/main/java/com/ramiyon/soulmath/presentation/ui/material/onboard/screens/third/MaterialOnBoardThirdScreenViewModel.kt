package com.ramiyon.soulmath.presentation.ui.material.onboard.screens.third

import androidx.lifecycle.ViewModel
import com.ramiyon.soulmath.domain.usecase.material.onboard.MaterialOnBoardUseCase

class MaterialOnBoardThirdScreenViewModel(
    private val useCase: MaterialOnBoardUseCase
) : ViewModel() {

    private fun getMaterialOnBoardThirdScreen(materialId: String) = useCase.getMaterialOnBoardContentById(materialId, 3)
}