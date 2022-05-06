package com.ramiyon.soulmath.presentation.common

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.LinearLayout
import com.ramiyon.soulmath.databinding.LottieDialogBinding

fun Context.buildLottieDialog(lottieBinding: LottieDialogBinding, fileName: String) =
    Dialog(this).apply {
        lottieBinding.lavAnimation.apply {
            setAnimation(fileName)
            repeatCount = 100
            playAnimation()
        }
        setContentView(lottieBinding.root)
        setCanceledOnTouchOutside(false)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val metrics = resources.displayMetrics
        val width = metrics.widthPixels
        this.window?.setLayout(6 * width / 7, LinearLayout.LayoutParams.WRAP_CONTENT)
    }