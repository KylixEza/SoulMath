package com.ramiyon.soulmath.presentation.diff_callback

class LastMaterialDiffCallback(
    oldList: List<String>,
    newList: List<String>
): BaseDiffUtil<String, Int>(oldList, newList) {

    override fun String.getItemIdentifier(): Int {
        return this.toInt()
    }
}