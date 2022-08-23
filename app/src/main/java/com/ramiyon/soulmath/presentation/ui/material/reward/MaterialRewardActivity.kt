package com.ramiyon.soulmath.presentation.ui.material.reward

import com.ramiyon.soulmath.base.BaseActivity
import com.ramiyon.soulmath.databinding.ActivityMaterialRewardBinding
import com.ramiyon.soulmath.util.Constanta.ARG_XP
import com.ramiyon.soulmath.util.Resource
import com.ramiyon.soulmath.util.ScreenOrientation
import org.koin.androidx.viewmodel.ext.android.viewModel

class MaterialRewardActivity : BaseActivity<ActivityMaterialRewardBinding>() {

    private val viewModel by viewModel<MaterialRewardViewModel>()

    override fun inflateViewBinding(): ActivityMaterialRewardBinding {
        return ActivityMaterialRewardBinding.inflate(layoutInflater)
    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }

    override fun ActivityMaterialRewardBinding.binder() {
        val xpEarned = intent.getIntExtra(ARG_XP, 0)
        viewModel.increaseStudentXp(xpEarned).observe(this@MaterialRewardActivity) {
            when(it) {
                is Resource.Loading -> TODO()
                is Resource.Success -> TODO()
                is Resource.Error -> TODO()
                is Resource.Empty -> TODO()
            }
        }
    }

}