package com.ramiyon.soulmath.domain.usecase.material.onboard

import com.ramiyon.soulmath.domain.model.MaterialOnBoard

interface MaterialOnBoardUseCase {
    fun getMaterialOnBoardContentById(materialId: String, page: Int): MaterialOnBoard
    fun getMaterialLearningPurposeById(materialId: String): Pair<String, String>
}