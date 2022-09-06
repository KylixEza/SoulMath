package com.ramiyon.soulmath.data.source.remote

import com.ramiyon.soulmath.base.BaseRemoteResponse
import com.ramiyon.soulmath.data.source.remote.api.ApiService
import com.ramiyon.soulmath.data.source.remote.api.response.BaseResponse
import com.ramiyon.soulmath.data.source.remote.api.response.leaderboard.LeaderboardResponse
import com.ramiyon.soulmath.data.source.remote.api.response.learning_journey.LearningJourneyResponse
import com.ramiyon.soulmath.data.source.remote.api.response.material.MaterialDetailResponse
import com.ramiyon.soulmath.domain.model.material.MaterialDetail
import com.ramiyon.soulmath.data.source.remote.api.response.material.MaterialResponse
import com.ramiyon.soulmath.data.source.remote.api.response.student.StudentBody
import com.ramiyon.soulmath.data.source.remote.api.response.student.StudentResponse
import com.ramiyon.soulmath.data.util.FirebaseResponse
import com.ramiyon.soulmath.data.source.remote.firebase.FirebaseService
import com.ramiyon.soulmath.data.util.RemoteResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(
    private val apiService: ApiService,
    private val firebaseService: FirebaseService
) {

    suspend fun signUp(email: String, password: String, body: StudentBody): Flow<RemoteResponse<StudentResponse?>> = flow {
        firebaseService.createUserWithEmailAndPassword(email, password).collect { firebaseResponse ->
            when(firebaseResponse) {
                is FirebaseResponse.Success -> {
                    body.studentId = firebaseResponse.data
                    try {
                        val response = apiService.register(body)
                        val data = response.data
                        if (response.status == "200") {
                            emit(RemoteResponse.Success(data))
                        } else {
                            throw Exception(response.message)
                        }
                    } catch (e: Exception) {
                        emit(RemoteResponse.Error(e.message.toString()))
                    }
                }
                is FirebaseResponse.Error -> emit(RemoteResponse.Error(firebaseResponse.errorMessage))
                is FirebaseResponse.Empty -> emit(RemoteResponse.Empty())
            }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun signIn(email: String, password: String): Flow<RemoteResponse<StudentResponse?>> = flow {
        firebaseService.signInWithEmailAndPassword(email, password).collect { firebaseResponse ->
            when(firebaseResponse) {
                is FirebaseResponse.Success -> {
                    val studentId = firebaseResponse.data
                    try {
                        val response = apiService.login(studentId)
                        val data = response.data
                        if (response.status == "200") {
                            emit(RemoteResponse.Success(data))
                        } else {
                            throw Exception(response.message)
                        }
                    } catch (e: Exception) {
                        emit(RemoteResponse.Error(e.message.toString()))
                    }
                }
                is FirebaseResponse.Error -> emit(RemoteResponse.Error(firebaseResponse.errorMessage))
                is FirebaseResponse.Empty -> emit(RemoteResponse.Empty())
            }
        }
    }.flowOn(Dispatchers.IO)
    
    suspend fun fetchStudentDetail(studentId: String) =
        object : BaseRemoteResponse<StudentResponse>() {
            override suspend fun call(): BaseResponse<StudentResponse> {
                return apiService.login(studentId)
            }
        }.asFlow()

    suspend fun fetchLeaderboard() =
        object : BaseRemoteResponse<List<LeaderboardResponse>>() {
            override suspend fun call(): BaseResponse<List<LeaderboardResponse>> {
                return apiService.fetchLeaderboard()
            }
        }.asFlow()

    suspend fun fetchStudentRank(studentId: String) =
        object : BaseRemoteResponse<LeaderboardResponse>() {
            override suspend fun call(): BaseResponse<LeaderboardResponse> {
                return apiService.fetchStudentRank(studentId)
            }
        }.asFlow()

    suspend fun updateStudentProfile(studentId: String, body: StudentBody) =
        object : BaseRemoteResponse<String>() {
            override suspend fun call(): BaseResponse<String> {
                return apiService.updateStudentProfile(studentId, body)
            }
        }.asFlow()

    suspend fun increaseStudentXp(studentId: String, body: StudentBody, givenXp: Int) =
        object : BaseRemoteResponse<String>() {
            override suspend fun call(): BaseResponse<String> {
                val newXp = body.xp + givenXp
                body.xp = newXp
                return apiService.updateStudentXp(studentId, body)
            }
        }.asFlow()

    suspend fun decreaseStudentXp(studentId: String, body: StudentBody, costXp: Int) =
        object : BaseRemoteResponse<String>() {
            override suspend fun call(): BaseResponse<String> {
                val newXp = body.xp - costXp
                body.xp = newXp
                return apiService.updateStudentXp(studentId, body)
            }
        }.asFlow()

    suspend fun updateStudentXp(studentId: String, xp: Int) =
        object : BaseRemoteResponse<String>() {
            override suspend fun call(): BaseResponse<String> {
                val body = StudentBody()
                body.xp = xp
                return apiService.updateStudentXp(studentId, body)
            }
        }.asFlow()

    suspend fun fetchLearningJourney(studentId: String) =
        object : BaseRemoteResponse<List<LearningJourneyResponse>>() {
            override suspend fun call(): BaseResponse<List<LearningJourneyResponse>> {
                return apiService.fetchLearningJourney(studentId)
            }
        }.asFlow()

    suspend fun fetchMaterials(moduleId: String, studentId: String) =
        object : BaseRemoteResponse<List<MaterialResponse>>() {
            override suspend fun call(): BaseResponse<List<MaterialResponse>> {
                return apiService.fetchMaterials(moduleId, studentId)
            }
        }.asFlow()

    suspend fun fetchMaterialDetail(materialId: String, studentId: String) =
        object : BaseRemoteResponse<MaterialDetailResponse>() {
            override suspend fun call(): BaseResponse<MaterialDetailResponse> {
                return apiService.fetchMaterialDetail(materialId, studentId)
            }
        }.asFlow()

    suspend fun postFavorite(materialId: String, studentId: String) =
        object : BaseRemoteResponse<String>() {
            override suspend fun call(): BaseResponse<String> {
                return apiService.postFavorite(studentId, materialId)
            }
        }.asFlow()
    
    suspend fun deleteFavorite(materialId: String, studentId: String) =
        object : BaseRemoteResponse<String>() {
            override suspend fun call(): BaseResponse<String> {
                return apiService.deleteFavorite(studentId, materialId)
            }
        }.asFlow()
    
}