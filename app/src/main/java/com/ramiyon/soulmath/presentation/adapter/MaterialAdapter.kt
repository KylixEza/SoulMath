package com.ramiyon.soulmath.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.ramiyon.soulmath.R
import com.ramiyon.soulmath.base.BaseRecyclerViewAdapter
import com.ramiyon.soulmath.databinding.ItemListMaterialDashboardBinding
import com.ramiyon.soulmath.domain.model.material.Material
import com.ramiyon.soulmath.presentation.diff_callback.MaterialDiffUtil

class MaterialAdapter(
    private val context: Context
): BaseRecyclerViewAdapter<ItemListMaterialDashboardBinding, Material>() {
    override fun inflateViewBinding(parent: ViewGroup): ItemListMaterialDashboardBinding {
        return ItemListMaterialDashboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    @SuppressLint("SetTextI18n")
    override val binder: (Material, ItemListMaterialDashboardBinding) -> Unit = { item, binding ->
        binding.apply {
            Glide.with(context).load(item.materialImage).into(ivMaterial)
            tvMaterialSubModule.text = "Lihat Video ${item.subModuleTitle}"
            Glide.with(context)
                .load(if(position?.mod(2) == 0) R.drawable.ic_material_dashboard_path_left_to_right
                else R.drawable.ic_material_dashboard_path_right_to_left)
                .into(ivMaterialPath)
        }
    }

    override val diffUtilBuilder: (List<Material>, List<Material>) -> DiffUtil.Callback = {old, new ->
        MaterialDiffUtil(old, new)
    }

}