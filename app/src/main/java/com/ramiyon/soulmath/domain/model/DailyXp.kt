package com.ramiyon.soulmath.domain.model

data class DailyXp(
    val dailyXpId: String,
    val dailyXp: Int,
    val day: Int,
    val dayTaken: String?,
    val isTaken: Boolean
)