package com.ramiyon.soulmath.presentation.ui.material.onboard

import com.ramiyon.soulmath.base.BaseActivity
import com.ramiyon.soulmath.databinding.ActivityMaterialOnBoardBinding
import com.ramiyon.soulmath.util.ScreenOrientation

class MaterialOnBoardActivity : BaseActivity<ActivityMaterialOnBoardBinding>() {
    override fun inflateViewBinding(): ActivityMaterialOnBoardBinding {
        return ActivityMaterialOnBoardBinding.inflate(layoutInflater)
    }

    override fun determineScreenOrientation(): ScreenOrientation? {
        return ScreenOrientation.PORTRAIT
    }

    override fun ActivityMaterialOnBoardBinding?.binder() {

    }

}