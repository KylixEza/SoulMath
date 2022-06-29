package com.ramiyon.soulmath.domain.usecase.leaderboard

import com.ramiyon.soulmath.domain.model.Student
import com.ramiyon.soulmath.util.Resource
import kotlinx.coroutines.flow.Flow

interface LeaderboardUseCase {

    fun fetchLeaderboard(): Flow<Resource<List<Student>>>
    fun fetchStudentRank(): Flow<Resource<Student>>

}