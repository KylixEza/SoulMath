package com.ramiyon.soulmath.domain.usecase.material.onboard

import com.ramiyon.soulmath.domain.model.material.MaterialLearningPurpose
import com.ramiyon.soulmath.domain.model.material.MaterialOnBoard
import com.ramiyon.soulmath.util.Resource
import kotlinx.coroutines.flow.Flow

interface MaterialOnBoardUseCase {
    fun fetchMaterialOnBoardContentById(materialId: String, page: Int): Flow<Resource<MaterialOnBoard>>
    fun fetchMaterialLearningPurposeById(materialId: String): Flow<Resource<MaterialLearningPurpose>>
}