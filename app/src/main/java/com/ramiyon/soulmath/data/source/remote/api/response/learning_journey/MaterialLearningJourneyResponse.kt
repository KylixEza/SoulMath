package com.ramiyon.soulmath.data.source.remote.api.response.learning_journey

import com.google.gson.annotations.SerializedName

data class MaterialLearningJourneyResponse(
	@field:SerializedName("module_id")
	val moduleId: String,
	
	@field:SerializedName("material_percentage")
	val materialPercentage: Double,
	
	@field:SerializedName("current_material_id")
	val currentMaterialId: String,
)
