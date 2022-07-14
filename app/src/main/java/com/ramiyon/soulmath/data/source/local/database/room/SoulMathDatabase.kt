package com.ramiyon.soulmath.data.source.local.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ramiyon.soulmath.data.source.local.database.enitity.DailyXpEntity
import com.ramiyon.soulmath.data.source.local.database.enitity.StudentEntity

@Database(entities = [StudentEntity::class, DailyXpEntity::class], version = 1)
abstract class SoulMathDatabase: RoomDatabase() {

    abstract fun soulMathDao(): SoulMathDao

}