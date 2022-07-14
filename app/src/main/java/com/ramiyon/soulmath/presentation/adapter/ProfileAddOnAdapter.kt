package com.ramiyon.soulmath.presentation.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.ramiyon.soulmath.base.BaseRecyclerViewAdapter
import com.ramiyon.soulmath.databinding.ItemListProfileAddOnsBinding
import com.ramiyon.soulmath.util.ProfileAddOns

class ProfileAddOnAdapter(
    private val context: Context
): BaseRecyclerViewAdapter<ItemListProfileAddOnsBinding, Triple<Int, String, ProfileAddOns>>() {
    override fun inflateViewBinding(parent: ViewGroup): ItemListProfileAddOnsBinding {
        return ItemListProfileAddOnsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override val binder: (Triple<Int, String, ProfileAddOns>, ItemListProfileAddOnsBinding) -> Unit = { data, binding ->
        when(data.third) {
            ProfileAddOns.PROFILE -> binding.buildItemList(data)
            ProfileAddOns.FAVORITE -> binding.buildItemList(data)
            ProfileAddOns.PASSWORD -> binding.buildItemList(data)
            ProfileAddOns.CONTACT -> binding.buildItemList(data)
            ProfileAddOns.TERMS -> binding.buildItemList(data)
            ProfileAddOns.LOGOUT -> binding.buildItemList(data)
        }
    }

    override val diffUtilBuilder: (List<Triple<Int, String, ProfileAddOns>>, List<Triple<Int, String, ProfileAddOns>>) -> DiffUtil.Callback
        get() = TODO("Not yet implemented")

    private fun ItemListProfileAddOnsBinding.buildItemList(
        data: Triple<Int, String, ProfileAddOns>,
    ) {
        Glide.with(context)
            .load(data.first)
            .into(this.ivAddOnIcon)
        tvAddOnBehaviour.text = data.second
        if(data.third == ProfileAddOns.FAVORITE)
            viewSeparator.visibility = View.VISIBLE
    }
}