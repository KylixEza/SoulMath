package com.ramiyon.soulmath.domain.usecase.material.reward

import com.ramiyon.soulmath.domain.repository.SoulMathRepository

class MaterialRewardUseCaseImpl(
    private val repository: SoulMathRepository
): MaterialRewardUseCase {
    override fun increaseStudentXp(givenXp: Int) = repository.increaseStudentXp(givenXp)
    override fun unlockMaterial(materialId: String) = repository.unlockMaterial(materialId)
}