package com.ramiyon.soulmath.domain.usecase.home

import com.ramiyon.soulmath.domain.model.DailyXp
import com.ramiyon.soulmath.domain.model.Student
import com.ramiyon.soulmath.domain.model.learning_journey.LearningJourney
import com.ramiyon.soulmath.domain.repository.SoulMathRepository
import com.ramiyon.soulmath.util.Resource
import kotlinx.coroutines.flow.Flow

class HomeUseCaseImpl(
    private val repository: SoulMathRepository
) : HomeUseCase {
    override fun fetchLearningJourney(): Flow<Resource<List<LearningJourney>>> = repository.fetchLearningJourney()
    override fun getStudentDetail(): Flow<Resource<Student>> = repository.getStudentDetail()
    override fun getCurrentDailyXp(): Flow<Resource<DailyXp>> = repository.getCurrentDailyXp()
    override fun takeDailyXp(dailyXpId: String): Flow<Resource<String?>> = repository.takeDailyXp(dailyXpId)
}