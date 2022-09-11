package com.ramiyon.soulmath.presentation.ui.material.onboard.screens.first

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ramiyon.soulmath.domain.usecase.material.onboard.MaterialOnBoardUseCase
import kotlinx.coroutines.Dispatchers

class MaterialOnBoardFirstScreenViewModel(
    private val useCase: MaterialOnBoardUseCase
) : ViewModel() {
    
    fun getMaterialOnBoardFirstScreen(materialId: String) =
        useCase.fetchMaterialOnBoardContentById(materialId, 1).asLiveData(Dispatchers.IO)
}