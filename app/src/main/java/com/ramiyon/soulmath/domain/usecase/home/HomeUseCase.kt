package com.ramiyon.soulmath.domain.usecase.home

import com.ramiyon.soulmath.domain.model.learning_journey.LearningJourney
import com.ramiyon.soulmath.util.Resource
import kotlinx.coroutines.flow.Flow

interface HomeUseCase {
    fun fetchLearningJourney(): Flow<Resource<List<LearningJourney>>>
}