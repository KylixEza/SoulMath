package com.ramiyon.soulmath.presentation.ui.material.video

import com.ramiyon.soulmath.base.BaseActivity
import com.ramiyon.soulmath.databinding.ActivityMaterialVideoPlayerBinding
import com.ramiyon.soulmath.util.ScreenOrientation

class MaterialVideoPlayerActivity : BaseActivity<ActivityMaterialVideoPlayerBinding>() {
    override fun inflateViewBinding(): ActivityMaterialVideoPlayerBinding {
        return ActivityMaterialVideoPlayerBinding.inflate(layoutInflater)
    }

    override fun determineScreenOrientation(): ScreenOrientation? {
        return ScreenOrientation.PORTRAIT
    }

    override fun ActivityMaterialVideoPlayerBinding.binder() {

    }

}