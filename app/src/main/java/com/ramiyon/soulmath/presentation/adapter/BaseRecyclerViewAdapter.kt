package com.ramiyon.soulmath.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseRecyclerViewAdapter<VB: ViewBinding, ListType>
    : RecyclerView.Adapter<BaseRecyclerViewAdapter<VB, ListType>.BaseViewHolder>() {

    private val itemList = arrayListOf<ListType>().toMutableList()

    protected abstract fun inflateViewBinding(
        parent: ViewGroup
    ): VB

    protected abstract val bind: (ListType) -> Unit

    fun setAllData(data: List<ListType>) {
        itemList.apply {
            clear()
            addAll(data)
        }
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