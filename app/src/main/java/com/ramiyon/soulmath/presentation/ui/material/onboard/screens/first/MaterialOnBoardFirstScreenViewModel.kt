package com.ramiyon.soulmath.presentation.ui.material.onboard.screens.first

import androidx.lifecycle.ViewModel
import com.ramiyon.soulmath.domain.usecase.material.onboard.MaterialOnBoardUseCase

class MaterialOnBoardFirstScreenViewModel(
    private val useCase: MaterialOnBoardUseCase
) : ViewModel() {

    fun getMaterialOnBoardFirstScreen(materialId: String) = useCase.getMaterialOnBoardContentById(materialId, 1)
}