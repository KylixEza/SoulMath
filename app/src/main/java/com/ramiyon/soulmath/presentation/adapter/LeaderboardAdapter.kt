package com.ramiyon.soulmath.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.ramiyon.soulmath.R
import com.ramiyon.soulmath.base.BaseRecyclerViewAdapter
import com.ramiyon.soulmath.databinding.ItemListLeaderboardBinding
import com.ramiyon.soulmath.domain.model.Student
import com.ramiyon.soulmath.presentation.diff_callback.LeaderboardDiffUtil

class LeaderboardAdapter(
    private val context: Context
): BaseRecyclerViewAdapter<ItemListLeaderboardBinding, Student>() {

    override fun inflateViewBinding(parent: ViewGroup): ItemListLeaderboardBinding =
        ItemListLeaderboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    override val binder: (Student, ItemListLeaderboardBinding) -> Unit = { data, view ->
        view.apply {
            val rank = position?.plus(1)
            view.buildLeaderboardItem(data, rank)
        }
    }

    override val diffUtilBuilder: (List<Student>, List<Student>) -> DiffUtil.Callback = { old, new ->
        LeaderboardDiffUtil(old, new)
    }

    private fun ItemListLeaderboardBinding.buildLeaderboardItem(data: Student, rank: Int?) {
        this.apply {
            tvRank.text = rank.toString()
            ivRank.visibility = View.INVISIBLE
            Glide.with(context)
                .load(data.avatar)
                .placeholder(R.drawable.ilu_default_profile_picture)
                .into(ivAvatar)
            tvAccountName.text = data.username
            tvXpPoints.text = String.format("${data.xp} XP")
        }
    }

}