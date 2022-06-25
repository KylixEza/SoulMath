package com.ramiyon.soulmath.data.source.local.database.enitity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student")
data class StudentEntity(

    @PrimaryKey
    @ColumnInfo(name = "studentId")
    val studentId: String,

    @ColumnInfo(name = "address")
    val address: String,

    @ColumnInfo(name = "avatar")
    val avatar: String,

    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "phoneNumber")
    val phoneNumber: String,

    @ColumnInfo(name = "xp")
    val xp: Int

)
