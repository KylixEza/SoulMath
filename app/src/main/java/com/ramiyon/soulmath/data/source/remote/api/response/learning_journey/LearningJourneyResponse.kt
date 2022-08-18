package com.ramiyon.soulmath.data.source.remote.api.response.learning_journey

import com.google.gson.annotations.SerializedName

data class LearningJourneyResponse(

	@field:SerializedName("module_id")
	val moduleId: String,

	@field:SerializedName("module_title")
	val moduleTitle: String,

	@field:SerializedName("module_image")
	val moduleImage: String,

	@field:SerializedName("module_icon_locked")
	val moduleIconLocked: String,

	@field:SerializedName("module_icon_unlocked")
	val moduleIconUnlocked: String,

	@field:SerializedName("is_module_unlocked")
	val isModuleUnlocked: Boolean,

	@field:SerializedName("material_learning_journey")
	val materialLearningJourneyResponse: MaterialLearningJourneyResponse,

	@field:SerializedName("games_learning_journey")
	val gameLearningJourneyResponses: List<GameLearningJourneyResponse>
	
)