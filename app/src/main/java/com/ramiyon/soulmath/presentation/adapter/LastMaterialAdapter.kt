package com.ramiyon.soulmath.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ramiyon.soulmath.databinding.ItemListLastMaterialBinding

class LastMaterialAdapter: BaseRecyclerViewAdapter<ItemListLastMaterialBinding, Int>() {

    override fun inflateViewBinding(parent: ViewGroup): ItemListLastMaterialBinding =
        ItemListLastMaterialBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    override val bind: (Int) -> Unit = {

    }
}