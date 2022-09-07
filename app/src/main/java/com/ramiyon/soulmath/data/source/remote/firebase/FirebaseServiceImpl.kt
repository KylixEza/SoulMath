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
    
    override fun fetchMaterialOnBoardingContent(
        materialId: String,
        page: Int
    ): Flow<FirebaseResponse<List<MaterialOnBoardResponse>>> = flow {
        var materialOnBoards = mutableListOf<MaterialOnBoardResponse>()
        
        CoroutineScope(Dispatchers.IO).launch {
            materialOnBoards = materialRef
                .collection(FirestoreReference.ON_BOARDING.reference)
                .document(FirestoreReference.CONTENT.reference)
                .collection(materialId)
                .whereArrayContains("page", page)
                .get()
                .await()
                .toObjects(MaterialOnBoardResponse::class.java)
        }.join()
        
        if (materialOnBoards.isNotEmpty())
            emit(FirebaseResponse.Success(materialOnBoards))
         else
             //Emit empty to trigger flow catcher
             emit(FirebaseResponse.Empty)
        
    }.catch {
        emit(FirebaseResponse.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)
    
    override fun fetchMaterialOnBoardingLearningPurpose(materialId: String): Flow<FirebaseResponse<List<MaterialLearningPurposeResponse>>> =
        flow {
            var materialLearningPurpose = mutableListOf<MaterialLearningPurposeResponse>()
            
            CoroutineScope(Dispatchers.IO).launch {
                materialLearningPurpose = materialRef
                    .collection(FirestoreReference.ON_BOARDING.reference)
                    .document(FirestoreReference.LEARNING_PURPOSE.reference)
                    .collection(materialId)
                    .get()
                    .await()
                    .toObjects(MaterialLearningPurposeResponse::class.java)
            }.join()
            
            if (materialLearningPurpose.isNotEmpty())
                emit(FirebaseResponse.Success(materialLearningPurpose))
            else
                //Emit empty to trigger flow catcher
                emit(FirebaseResponse.Empty)
            
        }.catch {
            emit(FirebaseResponse.Error(it.message.toString()))
        }.flowOn(Dispatchers.IO)
    
}

