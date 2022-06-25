package com.ramiyon.soulmath.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ramiyon.soulmath.base.NetworkBoundRequest
import com.ramiyon.soulmath.data.source.local.LocalDataSource
import com.ramiyon.soulmath.data.source.local.database.enitity.StudentEntity
import com.ramiyon.soulmath.data.source.remote.RemoteDataSource
import com.ramiyon.soulmath.data.util.RemoteResponse
import com.ramiyon.soulmath.data.source.remote.api.response.student.StudentBody
import com.ramiyon.soulmath.data.source.remote.api.response.student.StudentResponse
import com.ramiyon.soulmath.domain.repository.SoulMathRepository
import com.ramiyon.soulmath.util.toStudentEntity
import kotlinx.coroutines.flow.Flow

class SoulMathRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): SoulMathRepository {

    private val loggedStudentId = Firebase.auth.currentUser?.uid

    override suspend fun savePrefRememberMe(isRemember: Boolean) = localDataSource.savePrefRememberMe(isRemember)

    override suspend fun savePrefHaveRunAppBefore(isFirstTime: Boolean) = localDataSource.savePrefHaveRunAppBefore(isFirstTime)

    override fun readPrefRememberMe(): Flow<Boolean> = localDataSource.readPrefRememberMe()

    override fun readPrefHaveRunAppBefore(): Flow<Boolean> = localDataSource.readPrefHaveRunAppBefore()

    override fun signUp(email: String, password: String, body: StudentBody)  =
        object : NetworkBoundRequest<StudentResponse?>() {
            override suspend fun createCall(): Flow<RemoteResponse<StudentResponse?>> {
                return remoteDataSource.signUp(email, password, body)
            }

            override suspend fun saveCallResult(data: StudentResponse?) {
                if (data != null) {
                    localDataSource.insertStudent(data.toStudentEntity())
                }
            }
        }.asFlow()

    override fun signIn(email: String, password: String) =
        object : NetworkBoundRequest<StudentResponse?>() {
            override suspend fun createCall(): Flow<RemoteResponse<StudentResponse?>> {
                return remoteDataSource.signIn(email, password)
            }

            override suspend fun saveCallResult(data: StudentResponse?) {
                var currentUser: StudentEntity? = null
                loggedStudentId?.let { localDataSource.getStudentDetail(it) }?.collect {
                    currentUser = it
                }
                if (currentUser != null) {
                    localDataSource.updateStudent(data!!.toStudentEntity())
                } else {
                    localDataSource.insertStudent(data!!.toStudentEntity())
                }
            }

        }.asFlow()
}