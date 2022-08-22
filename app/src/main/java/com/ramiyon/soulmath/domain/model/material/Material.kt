package com.ramiyon.soulmath.domain.model.material

data class Material(
	val subModuleId: String,
	val materialId: String,
	val materialImage: String,
	val subModuleTitle: String,
	val videoUrl: String,
	val xpEarned: Int,
	val isUnlocked: Boolean
)
