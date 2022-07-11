package com.ramiyon.soulmath.data.source.local.database.room

import androidx.room.*
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
}