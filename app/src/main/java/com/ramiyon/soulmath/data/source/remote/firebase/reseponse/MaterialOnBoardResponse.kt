package com.ramiyon.soulmath.data.source.remote.firebase.reseponse

data class MaterialOnBoardResponse(
    val materialId: String,
    val page: Int,
    val gif: Int = 0,
    val upperImage: Int = 0,
    val lowerImage: Int = 0,
    val description: String,
)