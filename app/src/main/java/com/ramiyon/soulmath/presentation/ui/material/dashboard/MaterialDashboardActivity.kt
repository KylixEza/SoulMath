package com.ramiyon.soulmath.presentation.ui.material.dashboard

import com.ramiyon.soulmath.base.BaseActivity
import com.ramiyon.soulmath.databinding.ActivityMaterialDashboardBinding
import com.ramiyon.soulmath.presentation.adapter.MaterialAdapter
import com.ramiyon.soulmath.util.ScreenOrientation
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MaterialDashboardActivity : BaseActivity<ActivityMaterialDashboardBinding>() {

    private val viewModel by viewModel<MaterialDashboardViewModel>()
    private val adapter by inject<MaterialAdapter>()

    override fun inflateViewBinding(): ActivityMaterialDashboardBinding {
        return ActivityMaterialDashboardBinding.inflate(layoutInflater)
    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }

    override fun ActivityMaterialDashboardBinding.binder() {

    }

}