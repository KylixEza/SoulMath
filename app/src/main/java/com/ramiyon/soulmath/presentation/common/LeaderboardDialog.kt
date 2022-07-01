package com.ramiyon.soulmath.presentation.common

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ramiyon.soulmath.R
import com.ramiyon.soulmath.databinding.DialogRankBinding
import com.ramiyon.soulmath.databinding.FragmentLeaderboardBinding
import com.ramiyon.soulmath.domain.model.Leaderboard
import com.ramiyon.soulmath.domain.model.Student

@SuppressLint("InflateParams")
fun Context.buildLeaderboardDialog(
    layoutInflater: LayoutInflater,
    data: Leaderboard?
) {
    val materialBuilder = MaterialAlertDialogBuilder(this).create()
    val binding = DialogRankBinding.inflate(layoutInflater)

    binding.apply {
        Glide.with(this@buildLeaderboardDialog)
            .load(data?.avatar)
            .into(binding.ivProfile)
        tvXp.text = getString(R.string.xp_earned, data?.xp)
        tvDescRank.text = getString(R.string.leaderboard_dialog_description, data?.rank)
        btnOk.setOnClickListener { materialBuilder.dismiss() }
        ivClose.setOnClickListener { materialBuilder.dismiss() }
        materialBuilder.setView(binding.root)
        materialBuilder.show()
    }
}