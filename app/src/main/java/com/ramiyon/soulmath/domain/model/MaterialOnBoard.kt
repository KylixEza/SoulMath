package com.ramiyon.soulmath.domain.model

data class MaterialOnBoard(
    val materialId: String,
    val page: Int,
    val gif: Int = 0,
    val upperImage: Int = 0,
    val lowerImage: Int = 0,
    val description: String,
)
