package com.ramiyon.soulmath.presentation.animation

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class DepthPageTransform: ViewPager2.PageTransformer {
    private val MIN_SCALE = 0.75F

    override fun transformPage(page: View, position: Float) {
        val pageWidth = page.width

        when {
            position < -1 -> {
                page.alpha = 0F
            }
            position <= 0 -> {
                page.alpha = 1F
                page.translationX = 0F
                page.scaleX = 1F
                page.scaleY = 1F
            }
            position <= 1 -> {
                page.alpha = 1 - position
                page.translationX = pageWidth * -position
                val scaleFactor = (MIN_SCALE + (1 - MIN_SCALE) * (1 - abs(position)))
                page.scaleX = scaleFactor
                page.scaleY = scaleFactor
            }
            else -> {
                page.alpha = 0F
            }
        }
    }
}