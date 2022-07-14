package com.ramiyon.soulmath.domain.repository

import com.ramiyon.soulmath.domain.model.DailyXp
import com.ramiyon.soulmath.domain.model.Leaderboard
import com.ramiyon.soulmath.domain.model.Student
import com.ramiyon.soulmath.util.Resource
import kotlinx.coroutines.flow.Flow

interface SoulMathRepository {

    fun getOnBoardTitle(page: Int): String
    fun getOnBoardSubtitle(page: Int): String

    suspend fun savePrefRememberMe(isRemember: Boolean)
    suspend fun savePrefHaveRunAppBefore(isFirstTime: Boolean)

    fun readPrefRememberMe(): Flow<Boolean>
    fun readPrefHaveRunAppBefore(): Flow<Boolean>

    fun signUp(email: String, password: String, student: Student): Flow<Resource<Unit>>
    fun signIn(email: String, password: String): Flow<Resource<Unit>>
    fun fetchLeaderboard(): Flow<Resource<List<Leaderboard>>>
    fun fetchStudentRank(): Flow<Resource<Leaderboard>>
    fun getStudentDetail(): Flow<Resource<Student>>
    fun updateStudentProfile(student: Student): Flow<Resource<String?>>
    fun increaseStudentXp(student: Student, givenXp: Int): Flow<Resource<String?>>
    fun decreaseStudentXp(student: Student, costXp: Int): Flow<Resource<String?>>
    fun getDailyXpList(): Flow<Resource<List<DailyXp>>>
    fun getCurrentDailyXp(): Flow<Resource<DailyXp>>
    fun takeDailyXp(dailyXpId: String): Flow<Resource<String?>>
}