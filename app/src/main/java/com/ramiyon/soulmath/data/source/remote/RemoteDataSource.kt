package com.ramiyon.soulmath.data.source.remote

import com.ramiyon.soulmath.data.source.remote.api.ApiService
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
        firebaseService.createUserWithEmailAndPassword(email, password).collect { response ->
            when(response) {
                is FirebaseResponse.Success -> {
                    body.studentId = response.data
                    try {
                        val student = apiService.singUp(body).data
                        emit(RemoteResponse.Success(student))
                    } catch (e: Exception) {
                        emit(RemoteResponse.Error(e.message.toString()))
                    }
                }
                is FirebaseResponse.Error -> emit(RemoteResponse.Error(response.errorMessage))
                is FirebaseResponse.Empty -> emit(RemoteResponse.Empty())
            }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun signIn(email: String, password: String): Flow<RemoteResponse<StudentResponse?>> = flow {
        firebaseService.signInWithEmailAndPassword(email, password).collect { response ->
            when(response) {
                is FirebaseResponse.Success -> {
                    try {
                        val student = apiService.signIn(response.data).data
                        emit(RemoteResponse.Success(student))
                    } catch (e: Exception) {
                        emit(RemoteResponse.Error(e.message.toString()))
                    }
                }
                is FirebaseResponse.Error -> emit(RemoteResponse.Error(response.errorMessage))
                is FirebaseResponse.Empty -> emit(RemoteResponse.Empty())
            }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun fetchLeaderboard() = flow {
        try {
            val response = apiService.fetchLeaderboard()
            val leaderboard = response.data
            if (response.status == "200") {
                if (response.count == 0) {
                    emit(RemoteResponse.Empty())
                } else {
                    emit(RemoteResponse.Success(leaderboard))
                }
            } else {
                throw Exception(response.message)
            }
        } catch (e: Exception) {
            emit(RemoteResponse.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

}