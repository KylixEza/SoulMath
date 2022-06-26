package com.ramiyon.soulmath.domain.usecase.leaderboard

import com.ramiyon.soulmath.domain.model.Student
import com.ramiyon.soulmath.domain.repository.SoulMathRepository
import com.ramiyon.soulmath.util.Resource
import kotlinx.coroutines.flow.Flow

class LeaderboardUseCaseImpl(
    private val repository: SoulMathRepository
): LeaderboardUseCase {
    override fun fetchLeaderboard(): Flow<Resource<List<Student>>> = repository.fetchLeaderboard()
}