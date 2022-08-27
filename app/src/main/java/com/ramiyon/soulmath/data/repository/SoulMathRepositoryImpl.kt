package com.ramiyon.soulmath.data.repository

import android.content.Context
import android.util.Log
import androidx.work.OneTimeWorkRequest
import com.ramiyon.soulmath.base.*
import com.ramiyon.soulmath.data.connectivity.NetworkConnectivityObserver
import com.ramiyon.soulmath.data.source.dummy.getOnBoardContentByPage
import com.ramiyon.soulmath.data.source.local.LocalDataSource
import com.ramiyon.soulmath.data.source.local.database.enitity.DailyXpEntity
import com.ramiyon.soulmath.data.source.local.database.enitity.LeaderboardEntity
import com.ramiyon.soulmath.data.source.local.database.enitity.StudentEntity
import com.ramiyon.soulmath.data.source.remote.RemoteDataSource
import com.ramiyon.soulmath.data.source.remote.api.response.leaderboard.LeaderboardResponse
import com.ramiyon.soulmath.data.source.remote.api.response.learning_journey.LearningJourneyResponse
import com.ramiyon.soulmath.data.source.remote.api.response.material.MaterialDetailResponse
import com.ramiyon.soulmath.data.source.remote.api.response.material.MaterialResponse
import com.ramiyon.soulmath.data.source.remote.api.response.student.StudentResponse
import com.ramiyon.soulmath.data.util.LocalAnswer
import com.ramiyon.soulmath.data.util.RemoteResponse
import com.ramiyon.soulmath.data.worker.StudentProfileWorker
import com.ramiyon.soulmath.data.worker.StudentXpWorker
import com.ramiyon.soulmath.data.worker.WorkerParams
import com.ramiyon.soulmath.domain.model.DailyXp
import com.ramiyon.soulmath.domain.model.Leaderboard
import com.ramiyon.soulmath.domain.model.Student
import com.ramiyon.soulmath.domain.model.learning_journey.LearningJourney
import com.ramiyon.soulmath.domain.model.material.Material
import com.ramiyon.soulmath.domain.model.material.MaterialDetail
import com.ramiyon.soulmath.domain.repository.SoulMathRepository
import com.ramiyon.soulmath.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

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

    override fun fetchLeaderboard(shouldFetch: Boolean): Flow<Resource<List<Leaderboard>>> =
        object : NetworkBoundWorker<List<LeaderboardResponse>?, List<LeaderboardEntity>, List<Leaderboard>>() {
            override suspend fun callApi(): Flow<RemoteResponse<List<LeaderboardResponse>?>> {
                return remoteDataSource.fetchLeaderboard()
            }

            override fun loadFromDatabase(): Flow<LocalAnswer<List<LeaderboardEntity>>> {
                return localDataSource.getLeaderboard()
            }

            override suspend fun saveToDatabase(data: List<LeaderboardResponse>?) {
                data?.forEach {
                    localDataSource.insertAllLeaderboard(it.toLeaderboardEntity())
                }
            }

            override fun mapApiToDomain(data: List<LeaderboardResponse>?): List<Leaderboard> {
                return data?.map { it.toLeaderboard() } ?: listOf()
            }

            override fun mapDatabaseToDomain(data: List<LeaderboardEntity>?): List<Leaderboard> {
                return data?.map { it.toLeaderboard() } ?: listOf()
            }

            override suspend fun shouldRefresh(): Boolean {
                return shouldFetch
            }

            override suspend fun isFirstTime(): Boolean {
                val list = mutableListOf<LeaderboardEntity>()
                CoroutineScope(Dispatchers.IO).launch {
                    localDataSource.getLeaderboard().collect {
                        when(it) {
                            is LocalAnswer.Error -> {
                                Log.d("shouldRefresh", "error")
                            }
                            is LocalAnswer.Empty -> { }
                            is LocalAnswer.Success -> { list.addAll(it.data) }
                        }
                    }
                }.join()
                return list.isEmpty()
            }
        }.asFlow()

    override fun fetchStudentRank(): Flow<Resource<Leaderboard>> =
       object : NetworkOnlyResource<Leaderboard, LeaderboardResponse?>() {
            override suspend fun createCall(): Flow<RemoteResponse<LeaderboardResponse?>> {
                Log.d("UID ", getCurrentStudentId()!!)
                return remoteDataSource.fetchStudentRank(getCurrentStudentId().toString())
            }


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

            override suspend fun uploadToServer(): Flow<RemoteResponse<String?>> {
                return remoteDataSource.updateStudentProfile(getCurrentStudentId()!!, student.toStudentBody())
            }

            override suspend fun saveToDatabase(): LocalAnswer<Unit> {
                return localDataSource.updateStudent(student.toStudentEntity())
            }

            override fun buildOneTimeWorker(): OneTimeWorkRequest.Builder {
                return OneTimeWorkRequest.Builder(StudentProfileWorker::class.java)
            }

        }.doWork()

    override fun increaseStudentXp(givenXp: Int) = object : DatabaseBoundWorker<String?>(context) {

            private suspend fun getStudent(): Student? {
                var student: Student? = null
                localDataSource.getStudentDetail(getCurrentStudentId()!!).collect {
                    student = when(it) {
                        is LocalAnswer.Success -> it.data.toStudent()
                        else -> null
                    }
                }
                return student
            }

            override suspend fun putParamsForWorkManager(): MutableMap<String, *> {
                return mutableMapOf(
                    WorkerParams.STUDENT_ID.param to getCurrentStudentId(),
                )
            }

            override suspend fun uploadToServer(): Flow<RemoteResponse<String?>> {
                val student = getStudent()
                return remoteDataSource.increaseStudentXp(getCurrentStudentId()!!, student!!.toStudentBody(), givenXp)
            }

            override suspend fun saveToDatabase(): LocalAnswer<Unit> {
                val student = getStudent()
                return localDataSource.increaseStudentXp(student!!.toStudentEntity(), givenXp)
            }

        override fun buildOneTimeWorker(): OneTimeWorkRequest.Builder {
            return OneTimeWorkRequest.Builder(StudentXpWorker::class.java)
        }

    }.doWork()


    override fun decreaseStudentXp(costXp: Int) =
        object : DatabaseBoundWorker<String?>(context) {

            private suspend fun getStudent(): Student? {
                var student: Student? = null
                localDataSource.getStudentDetail(getCurrentStudentId()!!).collect {
                    student = when(it) {
                        is LocalAnswer.Success -> it.data.toStudent()
                        else -> null
                    }
                }
                return student
            }

            override suspend fun putParamsForWorkManager(): MutableMap<String, *> {
                return mutableMapOf(
                    WorkerParams.STUDENT_ID.param to getCurrentStudentId(),
                )
            }

            override suspend fun uploadToServer(): Flow<RemoteResponse<String?>> {
                val student = getStudent()
                return remoteDataSource.decreaseStudentXp(getCurrentStudentId()!!, student!!.toStudentBody(), costXp)
            }

            override suspend fun saveToDatabase(): LocalAnswer<Unit> {
                val student = getStudent()
                return localDataSource.decreaseStudentXp(student!!.toStudentEntity(), costXp)
            }

            override fun buildOneTimeWorker(): OneTimeWorkRequest.Builder {
                return OneTimeWorkRequest.Builder(StudentXpWorker::class.java)
            }
        }.doWork()

    override fun fetchLearningJourney(): Flow<Resource<List<LearningJourney>>> =
        object : NetworkOnlyResource<List<LearningJourney>, List<LearningJourneyResponse>?>() {
            override suspend fun createCall(): Flow<RemoteResponse<List<LearningJourneyResponse>?>> {
                return remoteDataSource.fetchLearningJourney(getCurrentStudentId()!!)
            }

            override fun mapTransform(data: List<LearningJourneyResponse>?): List<LearningJourney> {
                return data?.map { it.toLearningJourney() }!!
            }
        }.asFlow()


    override fun getDailyXpList() =
        object : DatabaseOnlyResource<List<DailyXpEntity>, List<DailyXp>>() {
            override suspend fun loadFromDb(): Flow<LocalAnswer<List<DailyXpEntity>>> {
                return localDataSource.getDailyXpList()
            }

            override fun mapTransform(data: List<DailyXpEntity>): List<DailyXp> {
                return data.map { it.toDailyXp() }
            }
        }.asFlow()

    override fun isTodayTaken() =
        object : DatabaseOnlyResource<Boolean, Boolean>() {
            override suspend fun loadFromDb(): Flow<LocalAnswer<Boolean>> {
                return localDataSource.isTodayTaken()
            }

            override fun mapTransform(data: Boolean): Boolean {
                return data
            }
        }.asFlow()

    override fun getTodayTakenXp(): Flow<Resource<DailyXp>> =
        object : DatabaseOnlyResource<DailyXpEntity, DailyXp>() {
            override suspend fun loadFromDb(): Flow<LocalAnswer<DailyXpEntity>> {
                return localDataSource.getTodayTakenXp()
            }

            override fun mapTransform(data: DailyXpEntity): DailyXp {
                return data.toDailyXp()
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

    override fun takeDailyXp(dailyXpId: String): Flow<Resource<Unit>> =
        object : NetworkBoundRequest<String?>() {

            private suspend fun getTodayXp(): Int {
                var todayXp = 0
                localDataSource.getSelectedDailyXp(dailyXpId).collect {
                    if (it is LocalAnswer.Success) {
                        todayXp = it.data.dailyXp
                    }
                }
                return todayXp
            }

            private suspend fun getStudentXp(): Int {
                var studentXp = 0
                localDataSource.getStudentDetail(getCurrentStudentId()!!).collect {
                    if (it is LocalAnswer.Success) {
                        studentXp = it.data.xp
                    }
                }
                return studentXp
            }

            override suspend fun createCall(): Flow<RemoteResponse<String?>> {
                val newXp = getStudentXp().plus(getTodayXp())
                Log.d("TakeDailyXp createCall", newXp.toString())
                return remoteDataSource.updateStudentXp(getCurrentStudentId()!!, newXp)
            }

            override suspend fun saveCallResult(data: String?) {
                val newXp = getStudentXp().plus(getTodayXp())
                localDataSource.takeDailyXp(getCurrentStudentId()!!, dailyXpId, newXp)
            }
        }.asFlow()


    override suspend fun resetLeaderboard() = localDataSource.resetLeaderboard()

    override fun fetchMaterials(moduleId: String): Flow<Resource<List<Material>>> =
        object : NetworkOnlyResource<List<Material>, List<MaterialResponse>?>() {
            override suspend fun createCall(): Flow<RemoteResponse<List<MaterialResponse>?>> {
                return remoteDataSource.fetchMaterials(moduleId, getCurrentStudentId()!!)
            }
            override fun mapTransform(data: List<MaterialResponse>?): List<Material> {
                return data?.map { it.toMaterial() }!!
            }
        }.asFlow()

    override fun fetchMaterialDetail(materialId: String): Flow<Resource<MaterialDetail>> =
        object : NetworkOnlyResource<MaterialDetail, MaterialDetailResponse?>() {
            override suspend fun createCall(): Flow<RemoteResponse<MaterialDetailResponse?>> {
                return remoteDataSource.fetchMaterialDetail(materialId, getCurrentStudentId()!!)
            }

            override fun mapTransform(data: MaterialDetailResponse?): MaterialDetail {
                return data?.toMaterialDetail()!!
            }
        }.asFlow()
}