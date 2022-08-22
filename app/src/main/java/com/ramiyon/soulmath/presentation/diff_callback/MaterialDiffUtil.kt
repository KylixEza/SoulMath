package com.ramiyon.soulmath.presentation.diff_callback

import com.ramiyon.soulmath.base.BaseDiffUtil
import com.ramiyon.soulmath.domain.model.material.Material

class MaterialDiffUtil(
    private val oldList: List<Material>,
    private val newList: List<Material>
): BaseDiffUtil<Material, String>(oldList, newList) {
    override fun Material.getItemIdentifier(): String {
        return this.materialId
    }
}