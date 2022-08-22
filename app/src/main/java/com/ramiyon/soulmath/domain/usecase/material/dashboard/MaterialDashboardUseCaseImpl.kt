package com.ramiyon.soulmath.domain.usecase.material.dashboard

import com.ramiyon.soulmath.domain.model.material.Material
import com.ramiyon.soulmath.domain.model.material.MaterialDetail
import com.ramiyon.soulmath.domain.repository.SoulMathRepository
import com.ramiyon.soulmath.util.Resource
import kotlinx.coroutines.flow.Flow

class MaterialDashboardUseCaseImpl(
    private val repository: SoulMathRepository
): MaterialDashboardUseCase {

    override fun fetchMaterials(moduleId: String): Flow<Resource<List<Material>>> =
        repository.fetchMaterials(moduleId)

    override fun fetchMaterialDetail(materialId: String): Flow<Resource<MaterialDetail>> =
        repository.fetchMaterialDetail(materialId)
}