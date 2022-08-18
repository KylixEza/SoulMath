package com.ramiyon.soulmath.domain.model.learning_journey

data class GameDifficultyLearningJourney<T>(
	val easy: T,
	val medium: T,
	val hard: T
)
