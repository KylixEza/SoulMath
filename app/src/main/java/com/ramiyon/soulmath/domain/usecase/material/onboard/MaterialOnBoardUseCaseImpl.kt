package com.ramiyon.soulmath.domain.usecase.material.onboard

import com.ramiyon.soulmath.domain.repository.SoulMathRepository

class MaterialOnBoardUseCaseImpl(
    private val repository: SoulMathRepository
): MaterialOnBoardUseCase {
    override fun fetchMaterialOnBoardContentById(materialId: String) = repository.fetchMaterialOnBoardingContent(materialId)
    override fun fetchMaterialLearningPurposeById(materialId: String) = repository.fetchMaterialOnBoardingLearningPurpose(materialId)
}