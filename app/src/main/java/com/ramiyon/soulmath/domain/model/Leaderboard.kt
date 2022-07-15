package com.ramiyon.soulmath.domain.model

data class Leaderboard(
    val studentId: String,
    val username: String,
    val avatar: String,
    val xp: Int,
    val rank: Int
)
