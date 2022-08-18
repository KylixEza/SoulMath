package com.ramiyon.soulmath.domain.usecase.home

import com.ramiyon.soulmath.domain.model.learning_journey.LearningJourney
import com.ramiyon.soulmath.domain.repository.SoulMathRepository
import com.ramiyon.soulmath.util.Resource
import kotlinx.coroutines.flow.Flow

class HomeUseCaseImpl(
    private val repository: SoulMathRepository
) : HomeUseCase {
    override fun fetchLearningJourney(): Flow<Resource<List<LearningJourney>>> = repository.fetchLearningJourney()
}