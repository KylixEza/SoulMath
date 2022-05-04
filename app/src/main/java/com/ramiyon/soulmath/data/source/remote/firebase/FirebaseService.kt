package com.ramiyon.soulmath.data.source.remote.firebase

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kiwimob.firestore.coroutines.await
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class FirebaseService {

    val auth = Firebase.auth
    fun getCurrentUserId() = auth.currentUser?.uid

    fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<FirebaseResponse<String>> =
        flow {
            val createUser = auth.createUserWithEmailAndPassword(email, password).await()
            val user = createUser.user
            if (user != null) {
                emit(FirebaseResponse.Success(user.uid))
            } else {
                emit(FirebaseResponse.Empty)
            }
        }.catch {
            emit(FirebaseResponse.Error(it.message.toString()))
        }.flowOn(Dispatchers.IO)


    fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<FirebaseResponse<String>> =
        flow {
            val singInUser = auth.signInWithEmailAndPassword(email, password).await()
            val user = singInUser.user
            if (user != null) {
                emit(FirebaseResponse.Success(user.uid))
            } else {
                emit(FirebaseResponse.Empty)
            }
        }.catch {
            emit(FirebaseResponse.Error(it.message.toString()))
        }.flowOn(Dispatchers.IO)
}

