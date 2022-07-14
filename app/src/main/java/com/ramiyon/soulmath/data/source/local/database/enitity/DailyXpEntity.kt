package com.ramiyon.soulmath.data.source.local.database.enitity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dailyXp")
data class DailyXpEntity(
    @PrimaryKey
    @ColumnInfo(name = "dailyXpId")
    val dailyXpId: String,

    @ColumnInfo(name = "dailyXp")
    val dailyXp: Int,

    @ColumnInfo(name = "day")
    val day: Int,

    @ColumnInfo(name = "isTaken")
    val isTaken: Boolean
)
