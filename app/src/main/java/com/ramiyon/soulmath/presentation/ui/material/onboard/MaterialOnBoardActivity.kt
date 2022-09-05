package com.ramiyon.soulmath.presentation.ui.material.onboard

import androidx.navigation.navArgs
import com.ramiyon.soulmath.base.BaseActivity
import com.ramiyon.soulmath.databinding.ActivityMaterialOnBoardBinding
import com.ramiyon.soulmath.presentation.adapter.OnBoardingViewPagerAdapter
import com.ramiyon.soulmath.presentation.ui.material.onboard.screens.first.MaterialOnBoardFirstScreenFragment
import com.ramiyon.soulmath.presentation.ui.material.onboard.screens.second.MaterialOnBoardSecondScreenFragment
import com.ramiyon.soulmath.presentation.ui.material.onboard.screens.third.MaterialOnBoardThirdScreenFragment
import com.ramiyon.soulmath.util.ScreenOrientation

class MaterialOnBoardActivity : BaseActivity<ActivityMaterialOnBoardBinding>() {

    private val args: MaterialOnBoardActivityArgs by navArgs()

    override fun inflateViewBinding(): ActivityMaterialOnBoardBinding {
        return ActivityMaterialOnBoardBinding.inflate(layoutInflater)
    }

    override fun determineScreenOrientation(): ScreenOrientation? = null

    override fun ActivityMaterialOnBoardBinding.binder() {

        setSupportActionBar(materialOnBoardingToolbar)
        supportActionBar?.hide()
    
        val materialId = args.materialId
        val moduleId = args.moduleId
        val moduleTitle = args.moduleTitle
        
        tvToolbarTitle.text = moduleTitle
        ivBack.setOnClickListener { finish() }

        val adapter = OnBoardingViewPagerAdapter(
            supportFragmentManager,
            lifecycle
        )

        val fragments = listOf(
            MaterialOnBoardFirstScreenFragment.getInstance(materialId),
            MaterialOnBoardSecondScreenFragment.getInstance(materialId),
            MaterialOnBoardThirdScreenFragment.getInstance(materialId, moduleId, moduleTitle)
        )

        adapter.apply {
            setAllFragments(fragments)
            vpMaterialOnboard.adapter = this
        }
        pageIndicatorViewMaterialOnboard.setViewPager2(vpMaterialOnboard)
    }
    
    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}