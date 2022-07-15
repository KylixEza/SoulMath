package com.ramiyon.soulmath.data.source.local.database.room

import androidx.room.*
import com.ramiyon.soulmath.data.source.local.database.enitity.DailyXpEntity
import com.ramiyon.soulmath.data.source.local.database.enitity.LeaderboardEntity
import com.ramiyon.soulmath.data.source.local.database.enitity.StudentEntity
import com.ramiyon.soulmath.domain.model.DailyXp
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllDailyXp(dailyXpEntity: DailyXpEntity)

    @Query("SELECT * FROM dailyXp")
    fun getDailyXpList(): Flow<List<DailyXpEntity>>

    @Query("SELECT * FROM dailyXp WHERE isTaken = 0 ORDER BY dailyXp ASC LIMIT 1")
    fun getCurrentDailyXp(): Flow<DailyXpEntity>

    @Query("UPDATE dailyXp SET isTaken = 1 WHERE dailyXpId = :dailyXpId")
    suspend fun takeDailyXp(dailyXpId: String)

    @Query("SELECT * FROM dailyXp WHERE dailyXpId = :dailyXpId")
    fun getSelectedDailyXp(dailyXpId: String): Flow<DailyXpEntity>

    @Query("UPDATE dailyXp SET isTaken = 0")
    suspend fun resetDailyXp()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllLeaderboard(entity: LeaderboardEntity)

    @Query("SELECT * FROM leaderboard")
    fun getLeaderboard(): Flow<List<LeaderboardEntity>>
}