package com.ramiyon.soulmath.domain.repository

import com.ramiyon.soulmath.base.DatabaseOnlyResource
import com.ramiyon.soulmath.domain.model.material.MaterialDetail
import com.ramiyon.soulmath.domain.model.DailyXp
import com.ramiyon.soulmath.domain.model.Leaderboard
import com.ramiyon.soulmath.domain.model.Student
import com.ramiyon.soulmath.domain.model.learning_journey.LearningJourney
import com.ramiyon.soulmath.domain.model.material.Material
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
    fun fetchLeaderboard(shouldFetch: Boolean): Flow<Resource<List<Leaderboard>>>
    fun fetchStudentRank(): Flow<Resource<Leaderboard>>
    fun getStudentDetail(): Flow<Resource<Student>>
    fun updateStudentProfile(student: Student): Flow<Resource<String?>>
    fun increaseStudentXp(givenXp: Int): Flow<Resource<String?>>
    fun decreaseStudentXp(costXp: Int): Flow<Resource<String?>>
    fun fetchLearningJourney(): Flow<Resource<List<LearningJourney>>>
    fun fetchMaterials(moduleId: String): Flow<Resource<List<Material>>>
    fun fetchMaterialDetail(materialId: String): Flow<Resource<MaterialDetail>>

    fun getDailyXpList(): Flow<Resource<List<DailyXp>>>
    fun getCurrentDailyXp(): Flow<Resource<DailyXp>>
    fun takeDailyXp(dailyXpId: String): Flow<Resource<Unit>>
    fun isTodayTaken(): Flow<Resource<Boolean>>
    fun getTodayTakenXp(): Flow<Resource<DailyXp>>
    suspend fun resetLeaderboard()
}