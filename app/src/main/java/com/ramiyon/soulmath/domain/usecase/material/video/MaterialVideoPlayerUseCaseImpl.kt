package com.ramiyon.soulmath.domain.usecase.material.video

import com.ramiyon.soulmath.domain.model.material.MaterialDetail
import com.ramiyon.soulmath.domain.repository.SoulMathRepository
import com.ramiyon.soulmath.util.Resource
import kotlinx.coroutines.flow.Flow

class MaterialVideoPlayerUseCaseImpl(
    private val repository: SoulMathRepository
): MaterialVideoPlayerUseCase {
    override fun fetchMaterialDetail(materialId: String): Flow<Resource<MaterialDetail>> =
        repository.fetchMaterialDetail(materialId)
    
    override fun postFavorite(materialId: String): Flow<Resource<String>> =
        repository.postFavorite(materialId)
    
    override fun deleteFavorite(materialId: String): Flow<Resource<String>> =
        repository.deleteFavorite(materialId)
}