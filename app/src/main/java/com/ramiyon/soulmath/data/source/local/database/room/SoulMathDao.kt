package com.ramiyon.soulmath.data.source.local.database.room

import androidx.room.*
import com.ramiyon.soulmath.data.source.local.database.enitity.DailyXpEntity
import com.ramiyon.soulmath.data.source.local.database.enitity.StudentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SoulMathDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(entity: StudentEntity)

    @Update
    suspend fun updateStudent(entity: StudentEntity)

    @Query("SELECT * FROM student WHERE studentId = :studentId")
    fun getStudentDetail(studentId: String): Flow<StudentEntity>

    @Query("UPDATE student SET xp = :xp WHERE studentId = :studentId")
    suspend fun updateStudentXp(studentId: String, xp: Int)

    @Query("SELECT * FROM dailyXp")
    suspend fun getDailyXpList(): Flow<List<DailyXpEntity>>

    @Query("SELECT * FROM dailyXp WHERE isTaken = 0 ORDER BY dailyXp ASC LIMIT 1")
    suspend fun getCurrentDailyXp(): Flow<DailyXpEntity>

    @Query("UPDATE dailyXp SET isTaken = 1 WHERE dailyXpId = :dailyXpId")
    suspend fun takeDailyXp(dailyXpId: String)

    @Query("UPDATE dailyXp SET isTaken = 0")
    suspend fun resetDailyXp()
}