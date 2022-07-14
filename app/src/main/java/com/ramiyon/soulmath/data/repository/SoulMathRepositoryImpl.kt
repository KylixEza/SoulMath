package com.ramiyon.soulmath.data.repository

import android.content.Context
import android.util.Log
import com.ramiyon.soulmath.base.*
import com.ramiyon.soulmath.data.source.dummy.getOnBoardContentByPage
import com.ramiyon.soulmath.data.source.local.LocalDataSource
import com.ramiyon.soulmath.data.source.local.database.enitity.DailyXpEntity
import com.ramiyon.soulmath.data.source.local.database.enitity.StudentEntity
import com.ramiyon.soulmath.data.source.remote.RemoteDataSource
import com.ramiyon.soulmath.data.source.remote.api.response.leaderboard.LeaderboardResponse
import com.ramiyon.soulmath.data.source.remote.api.response.student.StudentResponse
import com.ramiyon.soulmath.data.util.LocalAnswer
import com.ramiyon.soulmath.data.util.RemoteResponse
import com.ramiyon.soulmath.data.worker.WorkerCommand
import com.ramiyon.soulmath.data.worker.WorkerParams
import com.ramiyon.soulmath.domain.model.DailyXp
import com.ramiyon.soulmath.domain.model.Leaderboard
import com.ramiyon.soulmath.domain.model.Student
import com.ramiyon.soulmath.domain.repository.SoulMathRepository
import com.ramiyon.soulmath.util.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

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
                Log.e("get student detail", "getCurrentStudentId() = ${getCurrentStudentId()}")
                return localDataSource.getStudentDetail(getCurrentStudentId().toString())
            }

            override fun mapTransform(data: StudentEntity): Student {
                return data.toStudent()
            }
        }.asFlow()


    override fun updateStudentProfile(student: Student) =
        object : DatabaseBoundWorker<String?>(context) {
            override suspend fun putParamsForWorkManager(): MutableMap<String, *> {
                return mutableMapOf(
                    WorkerParams.STUDENT_ID.param to getCurrentStudentId()
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

    override fun increaseStudentXp(student: Student, givenXp: Int) =
        object : DatabaseBoundWorker<String?>(context) {
            override suspend fun putParamsForWorkManager(): MutableMap<String, *> {
                return mutableMapOf(
                    WorkerParams.STUDENT_ID.param to getStudentDetail(),
                )
            }

            override fun callWorkerCommand(): WorkerCommand {
                return WorkerCommand.WORKER_COMMAND_UPDATE_XP
            }

            override suspend fun uploadToServer(): Flow<RemoteResponse<String?>> {
                return remoteDataSource.increaseStudentXp(getCurrentStudentId()!!, student.toStudentBody(), givenXp)
            }

            override suspend fun saveToDatabase(): LocalAnswer<Unit> {
                return localDataSource.increaseStudentXp(student.toStudentEntity(), givenXp)
            }

        }.doWork()

    override fun decreaseStudentXp(student: Student, costXp: Int) =
        object : DatabaseBoundWorker<String?>(context) {
            override suspend fun putParamsForWorkManager(): MutableMap<String, *> {
                return mutableMapOf(
                    WorkerParams.STUDENT_ID.param to getStudentDetail(),
                )
            }

            override fun callWorkerCommand(): WorkerCommand {
                return WorkerCommand.WORKER_COMMAND_UPDATE_XP
            }

            override suspend fun uploadToServer(): Flow<RemoteResponse<String?>> {
                return remoteDataSource.decreaseStudentXp(getCurrentStudentId()!!, student.toStudentBody(), costXp)
            }

            override suspend fun saveToDatabase(): LocalAnswer<Unit> {
                return localDataSource.decreaseStudentXp(student.toStudentEntity(), costXp)
            }
        }.doWork()

    override fun getDailyXpList() =
        object : DatabaseOnlyResource<List<DailyXpEntity>, List<DailyXp>>() {
            override suspend fun loadFromDb(): Flow<LocalAnswer<List<DailyXpEntity>>> {
                return localDataSource.getDailyXpList()
            }

            override fun mapTransform(data: List<DailyXpEntity>): List<DailyXp> {
                return data.map { it.toDailyXp() }
            }
        }.asFlow()

    override fun getCurrentDailyXp() =
        object : DatabaseOnlyResource<DailyXpEntity, DailyXp>() {
            override suspend fun loadFromDb(): Flow<LocalAnswer<DailyXpEntity>> {
                return localDataSource.getCurrentDailyXp()
            }

            override fun mapTransform(data: DailyXpEntity): DailyXp {
                return data.toDailyXp()
            }

        }.asFlow()

    override fun takeDailyXp(dailyXpId: String) =
        object : DatabaseBoundWorker<String?>(context) {
            override suspend fun putParamsForWorkManager(): MutableMap<String, *> {
                return mutableMapOf(
                    WorkerParams.STUDENT_ID.param to getStudentDetail(),
                )
            }

            override fun callWorkerCommand(): WorkerCommand {
                return WorkerCommand.WORKER_COMMAND_UPDATE_XP
            }

            override suspend fun uploadToServer(): Flow<RemoteResponse<String?>> {
                var todayXp: Int? = null
                var studentXp: Int? = null
                localDataSource.getSelectedDailyXp(dailyXpId).collect {
                    if (it is LocalAnswer.Success) {
                        todayXp = it.data.dailyXp
                    }
                }
                localDataSource.getStudentDetail(getCurrentStudentId()!!).collect {
                    if (it is LocalAnswer.Success) {
                        studentXp = it.data.xp
                    }
                }

                val newXp = studentXp?.let { todayXp?.plus(it) }
                return remoteDataSource.updateStudentXp(getCurrentStudentId()!!, newXp!!)
            }

            override suspend fun saveToDatabase(): LocalAnswer<Unit> {
                var todayXp: Int? = null
                localDataSource.getSelectedDailyXp(dailyXpId).collect {
                    if (it is LocalAnswer.Success) {
                        todayXp = it.data.dailyXp
                    }
                }
                return localDataSource.takeDailyXp(getCurrentStudentId()!!, dailyXpId, todayXp)
            }

        }.doWork()


}