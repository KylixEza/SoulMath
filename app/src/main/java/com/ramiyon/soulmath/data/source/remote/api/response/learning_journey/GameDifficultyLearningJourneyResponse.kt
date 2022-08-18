package com.ramiyon.soulmath.data.source.remote.api.response.learning_journey

import com.google.gson.annotations.SerializedName

data class GameDifficultyLearningJourneyResponse<T>(
	
	@field:SerializedName("easy")
	val easy: T,
	
	@field:SerializedName("medium")
	val medium: T,
	
	@field:SerializedName("hard")
	val hard: T
	
)
