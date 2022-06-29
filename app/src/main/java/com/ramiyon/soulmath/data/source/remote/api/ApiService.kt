package com.ramiyon.soulmath.data.source.remote.api

import com.ramiyon.soulmath.data.source.remote.api.response.BaseResponse
import com.ramiyon.soulmath.data.source.remote.api.response.student.StudentBody
import com.ramiyon.soulmath.data.source.remote.api.response.student.StudentResponse
import retrofit2.http.Body
import retrofit2.http.Path

interface ApiService {

    suspend fun singUp(@Body studentBody: StudentBody): BaseResponse<StudentResponse>

    suspend fun signIn(@Path("studentId") studentId: String): BaseResponse<StudentResponse>

    suspend fun fetchLeaderboard(): BaseResponse<List<StudentResponse>>

    suspend fun fetchStudentRank(): BaseResponse<StudentResponse>
}