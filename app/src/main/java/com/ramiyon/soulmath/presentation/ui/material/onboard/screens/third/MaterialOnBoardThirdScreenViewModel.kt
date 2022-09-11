package com.ramiyon.soulmath.presentation.ui.material.onboard.screens.third

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ramiyon.soulmath.domain.usecase.material.onboard.MaterialOnBoardUseCase
import kotlinx.coroutines.Dispatchers

class MaterialOnBoardThirdScreenViewModel(
    private val useCase: MaterialOnBoardUseCase
) : ViewModel() {
    fun getMaterialOnBoardThirdScreen(materialId: String) =
        useCase.fetchMaterialOnBoardContentById(materialId, 3).asLiveData(Dispatchers.IO)
}