package com.ramiyon.soulmath.data.source.local.database.enitity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "leaderboard")
data class LeaderboardEntity(
    @PrimaryKey
    @ColumnInfo(name = "studentId")
    val studentId: String,

    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "avatar")
    val avatar: String,

    @ColumnInfo(name = "xp")
    val xp: Int,

    @ColumnInfo(name = "rank")
    val rank: Int
)
