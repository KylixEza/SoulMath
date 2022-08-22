package com.ramiyon.soulmath.domain.usecase.material.onboard

import com.ramiyon.soulmath.domain.model.material.MaterialLearningPurpose
import com.ramiyon.soulmath.domain.model.material.MaterialOnBoard

interface MaterialOnBoardUseCase {
    fun getMaterialOnBoardContentById(materialId: String, page: Int): MaterialOnBoard
    fun getMaterialLearningPurposeById(materialId: String): MaterialLearningPurpose
}