package com.ramiyon.soulmath.data.source.local

import com.ramiyon.soulmath.base.BaseDatabaseAnswer
import com.ramiyon.soulmath.data.source.local.database.enitity.DailyXpEntity
import com.ramiyon.soulmath.data.source.local.database.enitity.LeaderboardEntity
import com.ramiyon.soulmath.data.source.local.database.enitity.StudentEntity
import com.ramiyon.soulmath.data.source.local.database.room.SoulMathDao
import com.ramiyon.soulmath.data.source.local.datastore.SoulMathDataStore
import kotlinx.coroutines.flow.first

class LocalDataSource(
    private val dao: SoulMathDao,
    private val dataStore: SoulMathDataStore
) {

    suspend fun savePrefRememberMe(isRemember: Boolean) = dataStore.savePrefRememberMe(isRemember)
    suspend fun savePrefHaveRunAppBefore(isFirstTime: Boolean) = dataStore.savePrefHaveRunAppBefore(isFirstTime)
    suspend fun saveStudentId(studentId: String) = dataStore.savePrefStudentId(studentId)

    fun readPrefRememberMe() = dataStore.readPrefRememberMe()
    fun readPrefHaveRunAppBefore() = dataStore.readPrefHaveRunAppBefore()
    fun readPrefStudentId() = dataStore.readPrefStudentId()

    suspend fun insertStudent(studentEntity: StudentEntity) =
        object : BaseDatabaseAnswer<Unit>() {
            override suspend fun callDatabase() {
                dao.insertStudent(studentEntity)
            }
        }.doSingleEvent()

    suspend fun updateStudent(studentEntity: StudentEntity) =
        object : BaseDatabaseAnswer<Unit>() {
            override suspend fun callDatabase() {
                dao.updateStudent(studentEntity)
            }
        }.doSingleEvent()

    suspend fun increaseStudentXp(studentEntity: StudentEntity, givenXp: Int) =
        object : BaseDatabaseAnswer<Unit>() {
            override suspend fun callDatabase() {
                val newXp = studentEntity.xp + givenXp
                dao.updateStudentXp(studentEntity.studentId, newXp)
            }
        }.doSingleEvent()

    suspend fun decreaseStudentXp(studentEntity: StudentEntity, costXp: Int) =
        object : BaseDatabaseAnswer<Unit>() {
            override suspend fun callDatabase() {
                val newXp = studentEntity.xp - costXp
                dao.updateStudentXp(studentEntity.studentId, newXp)
            }
        }.doSingleEvent()

    fun getStudentDetail(studentId: String) =
        object : BaseDatabaseAnswer<StudentEntity>() {
            override suspend fun callDatabase(): StudentEntity {
                return dao.getStudentDetail(studentId).first()
            }
        }.doObservable()

    fun getDailyXpList() =
        object : BaseDatabaseAnswer<List<DailyXpEntity>>() {
            override suspend fun callDatabase(): List<DailyXpEntity> {
                return dao.getDailyXpList().first()
            }
        }.doObservable()

    fun getCurrentDailyXp() =
        object : BaseDatabaseAnswer<DailyXpEntity>() {
            override suspend fun callDatabase(): DailyXpEntity {
                return dao.getCurrentDailyXp().first()
            }
        }.doObservable()

    fun getSelectedDailyXp(dailyXpId: String) =
        object : BaseDatabaseAnswer<DailyXpEntity>() {
            override suspend fun callDatabase(): DailyXpEntity {
                return dao.getSelectedDailyXp(dailyXpId).first()
            }
        }.doObservable()

    suspend fun takeDailyXp(studentId: String, dailyXpId: String, givenXp: Int?) = object : BaseDatabaseAnswer<Unit>() {
            override suspend fun callDatabase() {
                givenXp?.let { dao.updateStudentXp(studentId, it) }
                dao.takeDailyXp(dailyXpId)
            }
        }.doSingleEvent()

    suspend fun resetDailyXp() = object : BaseDatabaseAnswer<Unit>() {
        override suspend fun callDatabase() {
            dao.resetDailyXp()
        }
    }.doSingleEvent()

    suspend fun insertAllLeaderboard(leaderboard: LeaderboardEntity) = object : BaseDatabaseAnswer<Unit>() {
        override suspend fun callDatabase() {
            dao.insertAllLeaderboard(leaderboard)
        }
    }.doSingleEvent()

    fun getLeaderboard() = object : BaseDatabaseAnswer<List<LeaderboardEntity>>() {
        override suspend fun callDatabase(): List<LeaderboardEntity> {
            return dao.getLeaderboard().first()
        }
    }.doObservable()
}