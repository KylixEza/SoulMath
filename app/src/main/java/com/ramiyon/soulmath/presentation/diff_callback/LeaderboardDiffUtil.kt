package com.ramiyon.soulmath.presentation.diff_callback

import com.ramiyon.soulmath.base.BaseDiffUtil
import com.ramiyon.soulmath.domain.model.Student

class LeaderboardDiffUtil(
    private val oldList: List<Student>,
    private val newList: List<Student>
): BaseDiffUtil<Student, String>(oldList, newList) {

    override fun Student.getItemIdentifier(): String = this.studentId
}