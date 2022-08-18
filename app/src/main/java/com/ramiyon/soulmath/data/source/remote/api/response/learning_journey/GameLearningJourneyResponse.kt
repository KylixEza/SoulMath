package com.ramiyon.soulmath.data.source.remote.api.response.learning_journey

import com.google.gson.annotations.SerializedName

data class GameLearningJourneyResponse(

	@field:SerializedName("module_id")
	val moduleId: String,

	@field:SerializedName("sub_module_id")
	val subModuleId: String,

	@field:SerializedName("sub_module_title")
	val subModuleTitle: String,

	@field:SerializedName("game_ids")
	val gameIds: GameDifficultyLearningJourneyResponse<String>,

	@field:SerializedName("is_game_unlocked")
	val isGameUnlocked: Boolean,

	@field:SerializedName("game_percentage")
	val gamePercentage: Double,

	@field:SerializedName("is_difficulties_unlocked")
	val isDifficultiesUnlocked: GameDifficultyLearningJourneyResponse<Boolean>,

	@field:SerializedName("is_difficulties_crowned")
	val isDifficultiesCrowned: GameDifficultyLearningJourneyResponse<Boolean>,
)
