package com.ramiyon.soulmath.domain.model.material

data class MaterialOnBoard(
    val materialId: String = "",
    val page: Int,
    val gif: String = "",
    val upperImage: String = "",
    val lowerImage: String = "",
    val description: String = "",
)
