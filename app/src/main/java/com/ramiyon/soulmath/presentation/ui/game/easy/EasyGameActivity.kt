package com.ramiyon.soulmath.presentation.ui.game.easy

import com.ramiyon.soulmath.base.BaseActivity
import com.ramiyon.soulmath.databinding.ActivityEasyGameBinding
import com.ramiyon.soulmath.util.ScreenOrientation

class EasyGameActivity : BaseActivity<ActivityEasyGameBinding>() {
    override fun inflateViewBinding(): ActivityEasyGameBinding = ActivityEasyGameBinding.inflate(layoutInflater)

    override fun determineScreenOrientation(): ScreenOrientation = ScreenOrientation.LANDSCAPE

    override fun ActivityEasyGameBinding.binder() {
        
    }
}