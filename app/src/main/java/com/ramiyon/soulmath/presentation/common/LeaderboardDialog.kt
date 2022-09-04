package com.ramiyon.soulmath.presentation.common

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.asLiveData
import com.bumptech.glide.Glide
import com.ramiyon.soulmath.R
import com.ramiyon.soulmath.databinding.DialogRankBinding
import com.ramiyon.soulmath.domain.model.Leaderboard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

@SuppressLint("InflateParams")
fun Context.buildLeaderboardDialog(
    lifecycleOwner: LifecycleOwner,
    rankBinding: DialogRankBinding,
    data: Leaderboard?
) = Dialog(this).apply {
    
    if(data == null)
        setContentView(rankBinding.root)
    
    with(rankBinding) {
        setLeaderboardDialogData(null).asLiveData(Dispatchers.Main).observe(lifecycleOwner) {
            Glide.with(this@buildLeaderboardDialog)
                .load(data?.avatar)
                .placeholder(R.drawable.ilu_default_profile_picture)
                .into(rankBinding.ivProfile)
            tvXp.text = getString(R.string.xp_earned, data?.xp)
            tvDescRank.text = getString(R.string.leaderboard_dialog_description, data?.rank)
            
        }
        btnOk.setOnClickListener { dismiss() }
        ivClose.setOnClickListener { dismiss() }
    }
    
    setCanceledOnTouchOutside(false)
    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    val metrics = resources.displayMetrics
    val width = metrics.widthPixels
    window?.setLayout(6 * width / 7, LinearLayout.LayoutParams.WRAP_CONTENT)
}

fun setLeaderboardDialogData(data: Leaderboard?) = flow { emit(data) }.flowOn(Dispatchers.Main)