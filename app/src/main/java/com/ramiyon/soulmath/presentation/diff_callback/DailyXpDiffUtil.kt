package com.ramiyon.soulmath.presentation.diff_callback

import com.ramiyon.soulmath.base.BaseDiffUtil
import com.ramiyon.soulmath.domain.model.DailyXp

class DailyXpDiffUtil(
    private val oldList: List<DailyXp>,
    private val newList: List<DailyXp>
): BaseDiffUtil<DailyXp, String>(oldList, newList) {
    override fun DailyXp.getItemIdentifier(): String = this.dailyXpId
}