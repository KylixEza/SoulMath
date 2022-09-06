package com.ramiyon.soulmath.presentation.ui.material.video

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ramiyon.soulmath.domain.usecase.material.video.MaterialVideoPlayerUseCase

class MaterialVideoPlayerViewModel(
    private val useCase: MaterialVideoPlayerUseCase
): ViewModel() {
    fun fetchMaterialDetail(materialId: String) = useCase.fetchMaterialDetail(materialId).asLiveData()
    fun postFavorite(materialId: String) = useCase.postFavorite(materialId).asLiveData()
    fun deleteFavorite(materialId: String) = useCase.deleteFavorite(materialId).asLiveData()
}