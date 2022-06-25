package com.ramiyon.soulmath.domain.usecase.leaderboard

import com.ramiyon.soulmath.domain.repository.SoulMathRepository

class LeaderboardImpl(
    private val repository: SoulMathRepository
): LeaderboardUseCase {
}