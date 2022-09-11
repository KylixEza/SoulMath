package com.ramiyon.soulmath.presentation.ui.material.onboard.screens.second

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ramiyon.soulmath.domain.usecase.material.onboard.MaterialOnBoardUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class MaterialOnBoardSecondScreenViewModel(
    private val useCase: MaterialOnBoardUseCase
) : ViewModel() {

    fun getMaterialOnBoardSecondScreen(materialId: String) =
        useCase.fetchMaterialOnBoardContentById(materialId, 2).asLiveData(Dispatchers.IO)
    
    fun getMaterialLearningPurposeById(materialId: String) =
        useCase.fetchMaterialLearningPurposeById(materialId).asLiveData(Dispatchers.IO)

}