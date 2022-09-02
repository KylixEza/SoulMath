package com.ramiyon.soulmath.util

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.target.Target

@SuppressLint("CheckResult")
fun callGlide(
    context: Context,
    load: Any,
    into: ImageView
) {
    Glide.with(context)
        .load(load)
        .apply {
            fitCenter()
            format(DecodeFormat.PREFER_ARGB_8888)
            override(Target.SIZE_ORIGINAL)
        }
        .dontTransform()
        .into(into)
}