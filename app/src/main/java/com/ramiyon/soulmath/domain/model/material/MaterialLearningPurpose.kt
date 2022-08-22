package com.ramiyon.soulmath.domain.model.material

data class MaterialLearningPurpose(
    val materialId: String,
    val chapter: String,
    val purposes: List<String>
)
