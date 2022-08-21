package com.ramiyon.soulmath.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.ramiyon.soulmath.base.BaseRecyclerViewAdapter
import com.ramiyon.soulmath.databinding.ItemListLearningPurposeBinding
import com.ramiyon.soulmath.presentation.diff_callback.LearningPurposeDiffCallback

class LearningPurposeAdapter: BaseRecyclerViewAdapter<ItemListLearningPurposeBinding, String>() {

    override fun inflateViewBinding(parent: ViewGroup): ItemListLearningPurposeBinding {
        return ItemListLearningPurposeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override val binder: (String, ItemListLearningPurposeBinding) -> Unit = { item, binding ->
        binding.apply {
            tvNumber.text = position?.plus(1).toString()
            tvLearningPurpose.text = item
        }
    }

    override val diffUtilBuilder: (List<String>, List<String>) -> DiffUtil.Callback = { old, new ->
        LearningPurposeDiffCallback(old, new)
    }
}