package com.ramiyon.soulmath.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.ramiyon.soulmath.base.BaseRecyclerViewAdapter
import com.ramiyon.soulmath.databinding.ItemListLastMaterialBinding
import com.ramiyon.soulmath.presentation.diff_callback.LastMaterialDiffCallback

class LastMaterialAdapter: BaseRecyclerViewAdapter<ItemListLastMaterialBinding, String>() {

    override fun inflateViewBinding(parent: ViewGroup): ItemListLastMaterialBinding =
        ItemListLastMaterialBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    override val binder: (String, ItemListLastMaterialBinding) -> Unit = { data, view ->

    }

    override val diffUtilBuilder: (List<String>, List<String>) -> DiffUtil.Callback = { oldItem, newItem ->
        LastMaterialDiffCallback(oldItem, newItem)
    }
}