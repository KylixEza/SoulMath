package com.ramiyon.soulmath.presentation.diff_callback

import com.ramiyon.soulmath.base.BaseDiffUtil

class LearningPurposeDiffCallback(
    private val oldList: List<String>,
    private val newList: List<String>
): BaseDiffUtil<String, String>(oldList, newList) {
    override fun String.getItemIdentifier(): String = this
}