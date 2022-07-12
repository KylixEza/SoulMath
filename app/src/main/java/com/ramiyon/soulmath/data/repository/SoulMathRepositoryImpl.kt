package com.ramiyon.soulmath.data.repository

import android.content.Context
import android.util.Log
import com.ramiyon.soulmath.base.DatabaseBoundWorker
import com.ramiyon.soulmath.base.DatabaseOnlyResource
import com.ramiyon.soulmath.base.NetworkBoundRequest
import com.ramiyon.soulmath.base.NetworkOnlyResource
import com.ramiyon.soulmath.data.source.dummy.getOnBoardContentByPage
import com.ramiyon.soulmath.data.source.local.LocalDataSource
import com.ramiyon.soulmath.data.source.local.database.enitity.StudentEntity
import com.ramiyon.soulmath.data.source.remote.RemoteDataSource
import com.ramiyon.soulmath.data.source.remote.api.response.leaderboard.LeaderboardResponse
import com.ramiyon.soulmath.data.source.remote.api.response.student.StudentResponse
import com.ramiyon.soulmath.data.util.LocalAnswer
import com.ramiyon.soulmath.data.util.RemoteResponse
import com.ramiyon.soulmath.data.worker.WorkerCommand
import com.ramiyon.soulmath.data.worker.WorkerParams
import com.ramiyon.soulmath.domain.model.Leaderboard
import com.ramiyon.soulmath.domain.model.Student
import com.ramiyon.soulmath.domain.repository.SoulMathRepository
import com.ramiyon.soulmath.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class SoulMathRepositoryImpl(
    private val context: Context,
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): SoulMathRepository {

    suspend fun getCurrentStudentId(): String? = localDataSource.readPrefStudentId().first()

    override fun getOnBoardTitle(page: Int): String = getOnBoardContentByPage(page).first

    override fun getOnBoardSubtitle(page: Int): String = getOnBoardContentByPage(page).second

    override suspend fun savePrefRememberMe(isRemember: Boolean) = localDataSource.savePrefRememberMe(isRemember)

    override suspend fun savePrefHaveRunAppBefore(isFirstTime: Boolean) = localDataSource.savePrefHaveRunAppBefore(isFirstTime)

    override fun readPrefRememberMe(): Flow<Boolean> = localDataSource.readPrefRememberMe()

    override fun readPrefHaveRunAppBefore(): Flow<Boolean> = localDataSource.readPrefHaveRunAppBefore()

    override fun signUp(email: String, password: String, student: Student)  =
        object : NetworkBoundRequest<StudentResponse?>() {
            override suspend fun createCall(): Flow<RemoteResponse<StudentResponse?>> {
                return remoteDataSource.signUp(email, password, student.toStudentBody())
            }

            override suspend fun saveCallResult(data: StudentResponse?) {
                if (data != null) {
                    Log.d("save call sign up", data.toString())
                    localDataSource.insertStudent(data.toStudentEntity())
                    localDataSource.saveStudentId(data.studentId)
                }
            }
        }.asFlow()

    override fun signIn(email: String, password: String) =
        object : NetworkBoundRequest<StudentResponse?>() {
            override suspend fun createCall(): Flow<RemoteResponse<StudentResponse?>> {
                return remoteDataSource.signIn(email, password)
            }

            override suspend fun saveCallResult(data: StudentResponse?) {
                if (data != null) {
                    Log.d("save call sign in", data.toString())
                    localDataSource.insertStudent(data.toStudentEntity())
                    localDataSource.saveStudentId(data.studentId)
                }
            }

        }.asFlow()

    override fun fetchLeaderboard(): Flow<Resource<List<Leaderboard>>> =
        object : NetworkOnlyResource<List<Leaderboard>, List<LeaderboardResponse>?>() {
            override suspend fun createCall(): Flow<RemoteResponse<List<LeaderboardResponse>?>> =
                remoteDataSource.fetchLeaderboard()

            override fun mapTransform(data: List<LeaderboardResponse>?): List<Leaderboard> =
                data?.map { it.toLeaderboard() }!!

        }.asFlow()

    override fun fetchStudentRank(): Flow<Resource<Leaderboard>> =
       object : NetworkOnlyResource<Leaderboard, LeaderboardResponse?>() {
            override suspend fun createCall(): Flow<RemoteResponse<LeaderboardResponse?>> =
                remoteDataSource.fetchStudentRank(getCurrentStudentId().toString())

            override fun mapTransform(data: LeaderboardResponse?): Leaderboard =
                data?.toLeaderboard()!!

        }.asFlow()

    override fun getStudentDetail(): Flow<Resource<Student>> =
        object : DatabaseOnlyResource<StudentEntity, Student>() {
            override suspend fun loadFromDb(): Flow<LocalAnswer<StudentEntity>> {
                val studentId = getCurrentStudentId()
                return localDataSource.getStudentDetail(studentId!!)
            }

            override fun mapTransform(data: StudentEntity): Student {
                return data.toStudent()
            }
        }.asFlow()


    fun updateStudentProfile(student: Student) =
        object : DatabaseBoundWorker<String?>(context) {
            override fun putParamsForWorkManager(): MutableMap<String, *> {
                return mutableMapOf(
                    //TODO: Fix the student id param
                    WorkerParams.STUDENT_ID.param to "",
                    WorkerParams.STUDENT_BODY.param to student.toStudentBody()
                )
            }

            override fun callWorkerCommand(): WorkerCommand {
                return WorkerCommand.WORKER_COMMAND_UPDATE_PROFILE
            }

            override suspend fun uploadToServer(): Flow<RemoteResponse<String?>> {
                return remoteDataSource.updateStudentProfile(getCurrentStudentId()!!, student.toStudentBody())
            }

            override suspend fun saveToDatabase(): LocalAnswer<Unit> {
                return localDataSource.updateStudent(student.toStudentEntity())
            }

        }.doWork()

    fun updateStudentXp(student: Student) =
        object : DatabaseBoundWorker<String?>(context) {
            override fun putParamsForWorkManager(): MutableMap<String, *> {
                return mutableMapOf(
                    //TODO: Fix student id param
                    WorkerParams.STUDENT_ID.param to "",
                    WorkerParams.STUDENT_BODY.param to student.toStudentBody()
                )
            }

            override fun callWorkerCommand(): WorkerCommand {
                return WorkerCommand.WORKER_COMMAND_UPDATE_XP
            }

            override suspend fun uploadToServer(): Flow<RemoteResponse<String?>> {
                return remoteDataSource.updateStudentXp(getCurrentStudentId()!!, student.toStudentBody())
            }

            override suspend fun saveToDatabase(): LocalAnswer<Unit> {
                return localDataSource.updateStudentXp(student.toStudentEntity())
            }

        }.doWork()
}