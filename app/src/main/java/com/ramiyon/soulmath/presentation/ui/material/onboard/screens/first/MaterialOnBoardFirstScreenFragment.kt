package com.ramiyon.soulmath.presentation.ui.material.onboard.screens.first

import android.view.ViewGroup
import com.ramiyon.soulmath.base.BaseFragment
import com.ramiyon.soulmath.databinding.FragmentMaterialOnBoardFirstScreenBinding
import com.ramiyon.soulmath.util.ScreenOrientation

class MaterialOnBoardFirstScreenFragment : BaseFragment<FragmentMaterialOnBoardFirstScreenBinding>() {

    override fun inflateViewBinding(container: ViewGroup?): FragmentMaterialOnBoardFirstScreenBinding {
        return FragmentMaterialOnBoardFirstScreenBinding.inflate(layoutInflater, container, false)
    }

    override fun FragmentMaterialOnBoardFirstScreenBinding.binder() {

    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }

}