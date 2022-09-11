package com.ramiyon.soulmath.data.source.remote.firebase.reseponse


data class MaterialOnBoardResponse(
    val materialId: String = "",
    val page: Int = 0,
    val gif: String = "",
    val upperImage: String = "",
    val lowerImage: String = "",
    val description: String = "",
)