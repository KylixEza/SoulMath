package com.ramiyon.soulmath.data.source.remote.firebase.reseponse

data class MaterialLearningPurposeResponse(
    val materialId: String = "",
    val chapter: String = "",
    val purposes: List<String> = arrayListOf()
)
