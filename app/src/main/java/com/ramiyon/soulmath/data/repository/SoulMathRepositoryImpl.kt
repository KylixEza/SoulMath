package com.ramiyon.soulmath.data.repository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ramiyon.soulmath.base.NetworkBoundRequest
import com.ramiyon.soulmath.base.NetworkOnlyResource
import com.ramiyon.soulmath.data.source.local.LocalDataSource
import com.ramiyon.soulmath.data.source.local.database.enitity.StudentEntity
import com.ramiyon.soulmath.data.source.remote.RemoteDataSource
import com.ramiyon.soulmath.data.source.remote.api.response.leaderboard.LeaderboardResponse
import com.ramiyon.soulmath.data.source.remote.api.response.student.StudentBody
import com.ramiyon.soulmath.data.source.remote.api.response.student.StudentResponse
import com.ramiyon.soulmath.data.util.RemoteResponse
import com.ramiyon.soulmath.domain.model.Leaderboard
import com.ramiyon.soulmath.domain.repository.SoulMathRepository
import com.ramiyon.soulmath.util.Resource
import com.ramiyon.soulmath.util.toLeaderboard
import com.ramiyon.soulmath.util.toStudentEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

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

    override fun fetchLeaderboard(): Flow<Resource<List<Leaderboard>>> =
        object : NetworkOnlyResource<List<Leaderboard>, List<LeaderboardResponse>?>() {
            override suspend fun createCall(): Flow<RemoteResponse<List<LeaderboardResponse>?>> =
                remoteDataSource.fetchLeaderboard()

            override fun mapTransform(data: List<LeaderboardResponse>?): Flow<List<Leaderboard>> =
                flow { data?.map { it.toLeaderboard() } }

        }.asFlow()

    override fun fetchStudentRank(): Flow<Resource<Leaderboard>> {

        var studentId: String? = null
        runBlocking {
            withContext(Dispatchers.Default) {
                localDataSource.readPrefStudentId().collect {
                    studentId = it
                }
            }
        }

        return object : NetworkOnlyResource<Leaderboard, LeaderboardResponse?>() {
            override suspend fun createCall(): Flow<RemoteResponse<LeaderboardResponse?>> =
                remoteDataSource.fetchStudentRank(studentId.toString())

            override fun mapTransform(data: LeaderboardResponse?): Flow<Leaderboard> =
                flow { data?.toLeaderboard() }

        }.asFlow()
    }


}