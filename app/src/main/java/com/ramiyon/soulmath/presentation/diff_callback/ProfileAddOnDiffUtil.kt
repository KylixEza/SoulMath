package com.ramiyon.soulmath.presentation.diff_callback

import com.ramiyon.soulmath.base.BaseDiffUtil
import com.ramiyon.soulmath.util.ProfileAddOns

class ProfileAddOnDiffUtil(
    private val oldList: List<Triple<Int, String, ProfileAddOns>>,
    private val newList: List<Triple<Int, String, ProfileAddOns>>
): BaseDiffUtil<Triple<Int, String, ProfileAddOns>, String>(oldList, newList) {
    override fun Triple<Int, String, ProfileAddOns>.getItemIdentifier(): String {
        return this.second
    }
}