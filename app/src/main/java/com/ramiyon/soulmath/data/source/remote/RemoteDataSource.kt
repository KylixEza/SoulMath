package com.ramiyon.soulmath.data.source.remote

import com.ramiyon.soulmath.data.source.remote.api.response.UserBody
import com.ramiyon.soulmath.data.source.remote.api.response.UserResponse
import com.ramiyon.soulmath.data.source.remote.firebase.FirebaseResponse
import com.ramiyon.soulmath.data.source.remote.firebase.FirebaseService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(
    private val firebaseService: FirebaseService
) {

    suspend fun signUp(email: String, password: String, body: UserBody): Flow<RemoteResponse<UserResponse>> = flow {
        firebaseService.createUserWithEmailAndPassword(email, password).collect { response ->
            when(response) {
                is FirebaseResponse.Success -> {
                    body.uid = response.data
                    try {
                        /*apiService.postNewUser(body).data
                        val user = apiService.getDetailUser(response.data).data
                        emit(ApiResponse.Success(user))*/
                    } catch (e: Exception) {
                        emit(RemoteResponse.Error(e.message.toString()))
                    }
                }
                is FirebaseResponse.Error -> emit(RemoteResponse.Error(response.errorMessage))
                is FirebaseResponse.Empty -> emit(RemoteResponse.Empty())
            }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun signIn(email: String, password: String): Flow<RemoteResponse<UserResponse>> = flow {
        firebaseService.signInWithEmailAndPassword(email, password).collect { response ->
            when(response) {
                is FirebaseResponse.Success -> {
                    try {
                        /*val userResponse = apiService.getDetailUser(response.data).data
                        emit(RemoteResponse.Success(userResponse))*/
                    } catch (e: Exception) {
                        emit(RemoteResponse.Error(e.message.toString()))
                    }
                }
                is FirebaseResponse.Error -> emit(RemoteResponse.Error(response.errorMessage))
                is FirebaseResponse.Empty -> emit(RemoteResponse.Empty())
            }
        }
    }.flowOn(Dispatchers.IO)

}