package com.ramiyon.soulmath.data.source.remote.firebase

import com.ramiyon.soulmath.data.source.remote.firebase.reseponse.MaterialLearningPurposeResponse
import com.ramiyon.soulmath.data.source.remote.firebase.reseponse.MaterialOnBoardResponse
import com.ramiyon.soulmath.data.util.FirebaseResponse
import kotlinx.coroutines.flow.Flow

interface FirebaseService {
    fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<FirebaseResponse<String>>
    
    fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<FirebaseResponse<String>>
    
    fun fetchMaterialOnBoardingContent(
        materialId: String,
        page: Int
    ): Flow<FirebaseResponse<MaterialOnBoardResponse>>
    
    fun fetchMaterialOnBoardingLearningPurpose(
        materialId: String,
    ): Flow<FirebaseResponse<MaterialLearningPurposeResponse>>
}