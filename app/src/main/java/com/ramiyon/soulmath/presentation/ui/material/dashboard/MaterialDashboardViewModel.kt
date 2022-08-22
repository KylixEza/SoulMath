package com.ramiyon.soulmath.presentation.ui.material.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ramiyon.soulmath.domain.usecase.material.dashboard.MaterialDashboardUseCase

class MaterialDashboardViewModel(
    private val useCase: MaterialDashboardUseCase
): ViewModel() {

    fun fetchMaterials(moduleId: String) = useCase.fetchMaterials(moduleId).asLiveData()
}