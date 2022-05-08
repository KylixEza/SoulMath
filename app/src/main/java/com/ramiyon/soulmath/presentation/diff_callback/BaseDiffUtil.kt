package com.ramiyon.soulmath.presentation.diff_callback

import androidx.recyclerview.widget.DiffUtil

abstract class BaseDiffUtil<ListType, ItemIdentifier, ContentIdentifier>(
    private val oldList: List<ListType>,
    private val newList: List<ListType>
): DiffUtil.Callback() {

    abstract fun ListType.getItemIdentifier(): ItemIdentifier

    abstract fun ListType.getContentIdentifier(): ContentIdentifier

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].getItemIdentifier() == newList[newItemPosition].getItemIdentifier()

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].getContentIdentifier() == newList[newItemPosition].getContentIdentifier()
}