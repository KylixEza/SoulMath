package com.ramiyon.soulmath.data.source.local

import com.ramiyon.soulmath.base.BaseDatabaseAnswer
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

    fun getStudentDetail(studentId: String) =
        object : BaseDatabaseAnswer<StudentEntity>() {
            override suspend fun callDatabase(): StudentEntity {
                return dao.getStudentDetail(studentId).first()
            }
        }.doObservable()
}