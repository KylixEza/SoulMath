package com.ramiyon.soulmath.presentation.ui.game.hard

import com.ramiyon.soulmath.base.BaseActivity
import com.ramiyon.soulmath.databinding.ActivityHardGameBinding
import com.ramiyon.soulmath.util.ScreenOrientation

class HardGameActivity : BaseActivity<ActivityHardGameBinding>() {
    override fun inflateViewBinding(): ActivityHardGameBinding =
        ActivityHardGameBinding.inflate(layoutInflater)

    override fun determineScreenOrientation(): ScreenOrientation? = ScreenOrientation.LANDSCAPE

    override fun ActivityHardGameBinding.binder() {

    }
}