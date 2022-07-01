package com.ramiyon.soulmath.data.source.remote.api.response.leaderboard

import com.google.gson.annotations.SerializedName

data class LeaderboardResponse(
    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("avatar")
    val avatar: String,

    @field:SerializedName("xp")
    val xp: Int,

    @field:SerializedName("rank")
    val rank: Int

)
