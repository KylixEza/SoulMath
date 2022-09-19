package com.ramiyon.soulmath.data.source.remote.api

import com.ramiyon.soulmath.data.source.remote.api.response.BaseResponse
import com.ramiyon.soulmath.data.source.remote.api.response.leaderboard.LeaderboardResponse
import com.ramiyon.soulmath.data.source.remote.api.response.learning_journey.LearningJourneyResponse
import com.ramiyon.soulmath.data.source.remote.api.response.material.MaterialDetailResponse
import com.ramiyon.soulmath.domain.model.material.MaterialDetail
import com.ramiyon.soulmath.data.source.remote.api.response.material.MaterialResponse
import com.ramiyon.soulmath.data.source.remote.api.response.question.QuestionResponse
import com.ramiyon.soulmath.data.source.remote.api.response.student.StudentBody
import com.ramiyon.soulmath.data.source.remote.api.response.student.StudentResponse
import retrofit2.http.*

interface ApiService {

    @POST("/student/register")
    suspend fun register(@Body studentBody: StudentBody): BaseResponse<StudentResponse>

    @GET("/student/{studentId}/login")
    suspend fun login(@Path("studentId") studentId: String): BaseResponse<StudentResponse>

    @PUT("/student/{studentId}/profile")
    suspend fun updateStudentProfile(@Path("studentId") studentId: String, @Body studentBody: StudentBody): BaseResponse<String>

    @PUT("/student/{studentId}/xp")
    suspend fun updateStudentXp(@Path("studentId")studentId: String, @Body studentBody: StudentBody): BaseResponse<String>

    @GET("/leaderboard")
    suspend fun fetchLeaderboard(): BaseResponse<List<LeaderboardResponse>>

    @GET("/leaderboard/{studentId}")
    suspend fun fetchStudentRank(@Path("studentId") studentId: String): BaseResponse<LeaderboardResponse>

    @GET("/{studentId}/learning-journey")
    suspend fun fetchLearningJourney(@Path("studentId") studentId: String): BaseResponse<List<LearningJourneyResponse>>

    @GET("/module/{moduleId}/materials/{studentId}")
    suspend fun fetchMaterials(
        @Path("moduleId") moduleId: String,
        @Path("studentId") studentId: String
    ): BaseResponse<List<MaterialResponse>>

    @GET("/material/{materialId}/{studentId}")
    suspend fun fetchMaterialDetail(
        @Path("materialId") materialId: String,
        @Path("studentId") studentId: String
    ): BaseResponse<MaterialDetailResponse>

    @GET("/game/{gameId}/questions")
    suspend fun fetchQuestions(
        @Path("gameId") gameId: String
    ): BaseResponse<List<QuestionResponse>>
    
    @POST("/favorite/{studentId}/{materialId}")
    suspend fun postFavorite(
        @Path("studentId") studentId: String,
        @Path("materialId") materialId: String
    ): BaseResponse<String>
    
    @DELETE("/favorite/{studentId}/{materialId}")
    suspend fun deleteFavorite(
        @Path("studentId") studentId: String,
        @Path("materialId") materialId: String
    ): BaseResponse<String>
}