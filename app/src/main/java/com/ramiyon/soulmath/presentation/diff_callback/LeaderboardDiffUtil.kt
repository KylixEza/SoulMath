package com.ramiyon.soulmath.presentation.diff_callback

import com.ramiyon.soulmath.base.BaseDiffUtil
import com.ramiyon.soulmath.domain.model.Leaderboard
import com.ramiyon.soulmath.domain.model.Student

class LeaderboardDiffUtil(
    private val oldList: List<Leaderboard>,
    private val newList: List<Leaderboard>
): BaseDiffUtil<Leaderboard, String>(oldList, newList) {

    override fun Leaderboard.getItemIdentifier(): String = this.username
}