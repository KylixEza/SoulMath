package com.ramiyon.soulmath.domain.model.learning_journey

data class LearningJourney(
	val moduleId: String,
	val moduleTitle: String,
	val moduleImage: String,
	val moduleIconLocked: String,
	val moduleIconUnlocked: String,
	val isModuleUnlocked: Boolean,
	val materialLearningJourneyResponse: MaterialLearningJourney,
	val gameLearningJourneyResponses: List<GameLearningJourney>
	
)