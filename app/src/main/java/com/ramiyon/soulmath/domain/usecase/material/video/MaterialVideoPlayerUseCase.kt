package com.ramiyon.soulmath.domain.usecase.material.video

import com.ramiyon.soulmath.domain.model.material.MaterialDetail
import com.ramiyon.soulmath.util.Resource
import kotlinx.coroutines.flow.Flow

interface MaterialVideoPlayerUseCase {
    fun fetchMaterialDetail(materialId: String): Flow<Resource<MaterialDetail>>
}