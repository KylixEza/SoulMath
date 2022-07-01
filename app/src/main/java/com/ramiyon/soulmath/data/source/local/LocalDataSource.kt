package com.ramiyon.soulmath.data.source.local

import com.ramiyon.soulmath.data.source.local.database.enitity.StudentEntity
import com.ramiyon.soulmath.data.source.local.database.room.SoulMathDao
import com.ramiyon.soulmath.data.source.local.datastore.SoulMathDataStore

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

    suspend fun insertStudent(studentEntity: StudentEntity) = dao.insertStudent(studentEntity)
    suspend fun updateStudent(studentEntity: StudentEntity) = dao.updateStudent(studentEntity)
    fun getStudentDetail(studentId: String) = dao.getStudentDetail(studentId)

}