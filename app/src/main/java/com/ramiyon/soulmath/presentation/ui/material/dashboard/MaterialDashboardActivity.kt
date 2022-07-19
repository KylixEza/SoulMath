package com.ramiyon.soulmath.presentation.ui.material.dashboard

import com.ramiyon.soulmath.base.BaseActivity
import com.ramiyon.soulmath.databinding.ActivityMaterialDashboardBinding
import com.ramiyon.soulmath.util.ScreenOrientation

class MaterialDashboardActivity : BaseActivity<ActivityMaterialDashboardBinding>() {
    override fun inflateViewBinding(): ActivityMaterialDashboardBinding {
        return ActivityMaterialDashboardBinding.inflate(layoutInflater)
    }

    override fun determineScreenOrientation(): ScreenOrientation? {
        return ScreenOrientation.PORTRAIT
    }

    override fun ActivityMaterialDashboardBinding?.binder() {

    }

}