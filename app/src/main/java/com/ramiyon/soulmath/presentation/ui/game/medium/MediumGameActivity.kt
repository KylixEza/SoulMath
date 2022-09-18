package com.ramiyon.soulmath.presentation.ui.game.medium

import com.ramiyon.soulmath.base.BaseActivity
import com.ramiyon.soulmath.databinding.ActivityMediumGameBinding
import com.ramiyon.soulmath.util.ScreenOrientation

class MediumGameActivity : BaseActivity<ActivityMediumGameBinding>() {
    override fun inflateViewBinding(): ActivityMediumGameBinding =
        ActivityMediumGameBinding.inflate(layoutInflater)

    override fun determineScreenOrientation(): ScreenOrientation = ScreenOrientation.LANDSCAPE

    override fun ActivityMediumGameBinding.binder() {

    }

}