package com.ramiyon.soulmath.presentation.diff_callback

import com.ramiyon.soulmath.base.BaseDiffUtil
import com.ramiyon.soulmath.domain.model.learning_journey.LearningJourney

class LearningJourneyDiffUtil(
    private val oldList: List<LearningJourney>,
    private val newList: List<LearningJourney>
): BaseDiffUtil<LearningJourney, String>(oldList, newList) {
    override fun LearningJourney.getItemIdentifier(): String {
        return this.moduleId
    }
}