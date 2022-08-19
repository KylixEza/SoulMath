package com.ramiyon.soulmath.domain.usecase.material.onboard

import com.ramiyon.soulmath.data.source.dummy.MaterialOnBoardContent

class MaterialOnBoardUseCaseImpl: MaterialOnBoardUseCase {
    override fun getMaterialOnBoardContentById(materialId: String, page: Int) = MaterialOnBoardContent.getMaterialOnBoardContentById(materialId, page)
    override fun getMaterialLearningPurposeById(materialId: String) = MaterialOnBoardContent.getMaterialLearningPurposeById(materialId)
}