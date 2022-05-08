package com.ramiyon.soulmath.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.ramiyon.soulmath.presentation.diff_callback.BaseDiffUtil

abstract class BaseRecyclerViewAdapter<VB: ViewBinding, ListType>
    : RecyclerView.Adapter<BaseRecyclerViewAdapter<VB, ListType>.BaseViewHolder>() {

    protected val itemList = arrayListOf<ListType>().toMutableList()

    protected abstract fun inflateViewBinding(
        parent: ViewGroup
    ): VB

    protected abstract val bind: (ListType) -> Unit

    protected abstract val diffUtilBuilder: (List<ListType>, List<ListType>) -> DiffUtil.Callback

    fun setAllData(data: List<ListType>) {
        val diffCallback = diffUtilBuilder(itemList, data)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        itemList.apply {
            clear()
            addAll(data)
        }

        diffResult.dispatchUpdatesTo(this)
    }

    inner class BaseViewHolder(val view: VB): RecyclerView.ViewHolder(view.root) {
        fun bind(item: ListType) {
            bind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = inflateViewBinding(parent)
        return BaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

}