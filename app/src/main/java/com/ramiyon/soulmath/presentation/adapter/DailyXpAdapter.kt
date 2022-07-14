package com.ramiyon.soulmath.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.ramiyon.soulmath.R
import com.ramiyon.soulmath.base.BaseRecyclerViewAdapter
import com.ramiyon.soulmath.databinding.ItemListCheckInBinding
import com.ramiyon.soulmath.domain.model.DailyXp
import com.ramiyon.soulmath.presentation.diff_callback.DailyXpDiffUtil

class DailyXpAdapter(
    private val context: Context
): BaseRecyclerViewAdapter<ItemListCheckInBinding, DailyXp>() {
    override fun inflateViewBinding(parent: ViewGroup): ItemListCheckInBinding {
        return ItemListCheckInBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    @SuppressLint("SetTextI18n")
    override val binder: (DailyXp, ItemListCheckInBinding) -> Unit = { data, binding ->
        binding.apply {
            tvXpEarned.text = "+ ${data.dailyXp}"
            if(data.isTaken)
                Glide.with(context).load(R.drawable.ic_check_in_yes).into(binding.ivCheckInCondition)
            else
                Glide.with(context).load(R.drawable.ic_check_in_no).into(binding.ivCheckInCondition)
        }
    }

    override val diffUtilBuilder: (List<DailyXp>, List<DailyXp>) -> DiffUtil.Callback = { old, new ->
        DailyXpDiffUtil(old, new)
    }
}