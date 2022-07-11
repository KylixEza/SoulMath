package com.ramiyon.soulmath.presentation.common

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.ramiyon.soulmath.R
import com.ramiyon.soulmath.databinding.DialogRankBinding
import com.ramiyon.soulmath.domain.model.Leaderboard

@SuppressLint("InflateParams")
fun Context.buildLeaderboardDialog(
    rankBinding: DialogRankBinding,
    data: Leaderboard?
) = Dialog(this).apply {

    with(rankBinding) {
        Glide.with(this@buildLeaderboardDialog)
            .load(data?.avatar)
            .placeholder(R.drawable.ilu_default_profile_picture)
            .into(rankBinding.ivProfile)
        tvXp.text = getString(R.string.xp_earned, data?.xp)
        tvDescRank.text = getString(R.string.leaderboard_dialog_description, data?.rank)
        btnOk.setOnClickListener { this@apply.dismiss() }
        ivClose.setOnClickListener { this@apply.dismiss() }
    }
    setContentView(rankBinding.root)
    setCanceledOnTouchOutside(false)
    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    val metrics = resources.displayMetrics
    val width = metrics.widthPixels
    this@apply.window?.setLayout(6 * width / 7, LinearLayout.LayoutParams.WRAP_CONTENT)
}
