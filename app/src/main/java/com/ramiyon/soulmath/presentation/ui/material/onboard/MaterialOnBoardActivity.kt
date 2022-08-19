package com.ramiyon.soulmath.presentation.ui.material.onboard

import com.ramiyon.soulmath.base.BaseActivity
import com.ramiyon.soulmath.databinding.ActivityMaterialOnBoardBinding
import com.ramiyon.soulmath.presentation.adapter.OnBoardingViewPagerAdapter
import com.ramiyon.soulmath.presentation.ui.material.onboard.screens.first.MaterialOnBoardFirstScreenFragment
import com.ramiyon.soulmath.presentation.ui.material.onboard.screens.second.MaterialOnBoardSecondScreenFragment
import com.ramiyon.soulmath.presentation.ui.material.onboard.screens.third.MaterialOnBoardThirdScreenFragment
import com.ramiyon.soulmath.util.ScreenOrientation

class MaterialOnBoardActivity : BaseActivity<ActivityMaterialOnBoardBinding>() {
    override fun inflateViewBinding(): ActivityMaterialOnBoardBinding {
        return ActivityMaterialOnBoardBinding.inflate(layoutInflater)
    }

    override fun determineScreenOrientation(): ScreenOrientation? = null

    override fun ActivityMaterialOnBoardBinding.binder() {
        val adapter = OnBoardingViewPagerAdapter(
            supportFragmentManager,
            lifecycle
        )

        val fragments = listOf(
            MaterialOnBoardFirstScreenFragment(),
            MaterialOnBoardSecondScreenFragment(),
            MaterialOnBoardThirdScreenFragment()
        )

        adapter.apply {
            setAllFragments(fragments)
            vpMaterialOnboard.adapter = this
        }
        pageIndicatorViewMaterialOnboard.setViewPager2(vpMaterialOnboard)
    }

}