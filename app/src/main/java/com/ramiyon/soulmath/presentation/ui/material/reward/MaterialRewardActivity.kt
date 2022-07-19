package com.ramiyon.soulmath.presentation.ui.material.reward

import com.ramiyon.soulmath.base.BaseActivity
import com.ramiyon.soulmath.databinding.ActivityMaterialRewardBinding
import com.ramiyon.soulmath.util.ScreenOrientation

class MaterialRewardActivity : BaseActivity<ActivityMaterialRewardBinding>() {
    override fun inflateViewBinding(): ActivityMaterialRewardBinding {
        return ActivityMaterialRewardBinding.inflate(layoutInflater)
    }

    override fun determineScreenOrientation(): ScreenOrientation? {
        return ScreenOrientation.PORTRAIT
    }

    override fun ActivityMaterialRewardBinding?.binder() {

    }

}