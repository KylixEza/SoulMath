package com.ramiyon.soulmath.presentation.ui.material.onboard.screens.third

import android.view.ViewGroup
import com.ramiyon.soulmath.base.BaseFragment
import com.ramiyon.soulmath.databinding.FragmentMaterialOnBoardThirdScreenBinding
import com.ramiyon.soulmath.util.ScreenOrientation

class MaterialOnBoardThirdScreenFragment : BaseFragment<FragmentMaterialOnBoardThirdScreenBinding>() {
    override fun inflateViewBinding(container: ViewGroup?): FragmentMaterialOnBoardThirdScreenBinding {
        return FragmentMaterialOnBoardThirdScreenBinding.inflate(layoutInflater, container, false)
    }

    override fun FragmentMaterialOnBoardThirdScreenBinding.binder() {

    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }


}