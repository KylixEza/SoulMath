package com.ramiyon.soulmath.domain.usecase.material.onboard

import com.ramiyon.soulmath.domain.model.MaterialOnBoard

interface MaterialOnBoardUseCase {
    fun getMaterialOnBoardContentById(materialId: String, page: Int): List<MaterialOnBoard>
    fun getMaterialLearningPurposeById(materialId: String): List<Pair<String, String>>
}