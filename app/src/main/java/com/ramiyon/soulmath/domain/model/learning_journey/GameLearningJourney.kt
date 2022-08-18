package com.ramiyon.soulmath.domain.model.learning_journey

data class GameLearningJourney(
	val moduleId: String,
	val subModuleId: String,
	val subModuleTitle: String,
	val gameIds: GameDifficultyLearningJourney<String>,
	val isGameUnlocked: Boolean,
	val gamePercentage: Double,
	val isDifficultiesUnlocked: GameDifficultyLearningJourney<Boolean>,
	val isDifficultiesCrowned: GameDifficultyLearningJourney<Boolean>,
)
