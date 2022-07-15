package com.ramiyon.soulmath.domain.usecase.leaderboard

import com.ramiyon.soulmath.domain.model.Leaderboard
import com.ramiyon.soulmath.domain.model.Student
import com.ramiyon.soulmath.util.Resource
import kotlinx.coroutines.flow.Flow

interface LeaderboardUseCase {

    fun fetchLeaderboard(shouldFetch: Boolean): Flow<Resource<List<Leaderboard>>>
    fun fetchStudentRank(): Flow<Resource<Leaderboard>>

}