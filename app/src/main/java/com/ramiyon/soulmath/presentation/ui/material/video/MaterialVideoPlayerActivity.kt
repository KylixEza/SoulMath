package com.ramiyon.soulmath.presentation.ui.material.video

import com.ramiyon.soulmath.base.BaseActivity
import com.ramiyon.soulmath.databinding.ActivityMaterialVideoPlayerBinding
import com.ramiyon.soulmath.util.ScreenOrientation
import org.koin.androidx.viewmodel.ext.android.viewModel

class MaterialVideoPlayerActivity : BaseActivity<ActivityMaterialVideoPlayerBinding>() {

    private val viewModel by viewModel<MaterialVideoPlayerViewModel>()

    override fun inflateViewBinding(): ActivityMaterialVideoPlayerBinding {
        return ActivityMaterialVideoPlayerBinding.inflate(layoutInflater)
    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.LANDSCAPE
    }

    override fun ActivityMaterialVideoPlayerBinding.binder() {

    }

}