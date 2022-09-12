package com.ramiyon.soulmath.data.source.remote.firebase

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kiwimob.firestore.coroutines.await
import com.ramiyon.soulmath.data.source.remote.firebase.reseponse.MaterialLearningPurposeResponse
import com.ramiyon.soulmath.data.source.remote.firebase.reseponse.MaterialOnBoardResponse
import com.ramiyon.soulmath.data.util.FirebaseResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FirebaseServiceImpl: FirebaseService {

    private val auth = Firebase.auth
    private val firestore = Firebase.firestore

    private val materialRef = firestore.collection(FirestoreReference.ADMIN.reference)
        .document(FirestoreReference.MATERIAL.reference)
    
    override fun createUserWithEmailAndPassword(
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


    override fun signInWithEmailAndPassword(
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
    
    override fun fetchMaterialOnBoardingContents(
        materialId: String
    ): Flow<FirebaseResponse<List<MaterialOnBoardResponse>>> = flow {
        var materialOnBoard: List<MaterialOnBoardResponse> = arrayListOf()
        
        CoroutineScope(Dispatchers.IO).launch {
            materialOnBoard = materialRef
                .collection(FirestoreReference.ON_BOARDING.reference)
                .document(FirestoreReference.CONTENT.reference)
                .collection(materialId)
                .get()
                .await()
                .toObjects(MaterialOnBoardResponse::class.java)
        }.join()

        if (materialOnBoard.isNotEmpty()) {
            emit(FirebaseResponse.Success(materialOnBoard))
        } else {
            emit(FirebaseResponse.Empty)
        }
        
    }.catch {
        emit(FirebaseResponse.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)
    
    override fun fetchMaterialOnBoardingLearningPurpose(materialId: String): Flow<FirebaseResponse<MaterialLearningPurposeResponse>> =
        flow {
            var materialLearningPurpose: MaterialLearningPurposeResponse? = null
            
            CoroutineScope(Dispatchers.IO).launch {
                materialLearningPurpose = materialRef
                    .collection(FirestoreReference.ON_BOARDING.reference)
                    .document(FirestoreReference.LEARNING_PURPOSE.reference)
                    .collection(materialId)
                    .whereEqualTo("materialId", materialId)
                    .get()
                    .await()
                    .toObjects(MaterialLearningPurposeResponse::class.java)
                    .first()
            }.join()
            
            if (materialLearningPurpose != null) {
                emit(FirebaseResponse.Success(materialLearningPurpose!!))
            } else {
                emit(FirebaseResponse.Empty)
            }
            
        }.catch {
            emit(FirebaseResponse.Error(it.message.toString()))
        }.flowOn(Dispatchers.IO)
    
}

