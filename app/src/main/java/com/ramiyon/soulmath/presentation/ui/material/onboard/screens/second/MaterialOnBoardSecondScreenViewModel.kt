package com.ramiyon.soulmath.presentation.ui.material.onboard.screens.second

import androidx.lifecycle.ViewModel
import com.ramiyon.soulmath.domain.usecase.material.onboard.MaterialOnBoardUseCase

class MaterialOnBoardSecondScreenViewModel(
    private val useCase: MaterialOnBoardUseCase
) : ViewModel() {

    fun getMaterialOnBoardSecondScreen(materialId: String) = useCase.getMaterialOnBoardContentById(materialId, 2)
    fun getMaterialLearningPurposeById(materialId: String) = useCase.getMaterialLearningPurposeById(materialId)

}