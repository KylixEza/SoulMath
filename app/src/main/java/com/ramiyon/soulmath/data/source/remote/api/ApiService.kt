package com.ramiyon.soulmath.data.source.remote.api

import com.ramiyon.soulmath.data.source.remote.api.response.BaseResponse
import com.ramiyon.soulmath.data.source.remote.api.response.leaderboard.LeaderboardResponse
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
    suspend fun updateStudentXp(@Path("studentId")studentId: String, @Field("xp") xp: Int): BaseResponse<String>

    @GET("/leaderboard")
    suspend fun fetchLeaderboard(): BaseResponse<List<LeaderboardResponse>>

    @GET("/leaderboard/{studentId}")
    suspend fun fetchStudentRank(@Path("studentId") studentId: String): BaseResponse<LeaderboardResponse>


}