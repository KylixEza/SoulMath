package com.ramiyon.soulmath.domain.usecase.material.dashboard

import com.ramiyon.soulmath.domain.model.material.Material
import com.ramiyon.soulmath.domain.model.material.MaterialDetail
import com.ramiyon.soulmath.util.Resource
import kotlinx.coroutines.flow.Flow

interface MaterialDashboardUseCase {

    fun fetchMaterials(moduleId: String): Flow<Resource<List<Material>>>
    fun fetchMaterialDetail(materialId: String): Flow<Resource<MaterialDetail>>

}