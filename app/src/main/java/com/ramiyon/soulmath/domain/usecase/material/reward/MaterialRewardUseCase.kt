package com.ramiyon.soulmath.domain.usecase.material.reward

import com.ramiyon.soulmath.util.Resource
import kotlinx.coroutines.flow.Flow

interface MaterialRewardUseCase {

    fun increaseStudentXp(givenXp: Int): Flow<Resource<Unit>>
    fun unlockMaterial(materialId: String): Flow<Resource<Unit>>
}