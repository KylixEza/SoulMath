package com.ramiyon.soulmath.domain.usecase.leaderboard

import com.ramiyon.soulmath.domain.model.Leaderboard
import com.ramiyon.soulmath.domain.repository.SoulMathRepository
import com.ramiyon.soulmath.util.Resource
import kotlinx.coroutines.flow.Flow

class LeaderboardUseCaseImpl(
    private val repository: SoulMathRepository
): LeaderboardUseCase {
    override fun fetchLeaderboard(shouldFetch: Boolean): Flow<Resource<List<Leaderboard>>> = repository.fetchLeaderboard(shouldFetch)
    override fun fetchStudentRank(): Flow<Resource<Leaderboard>> = repository.fetchStudentRank()

}