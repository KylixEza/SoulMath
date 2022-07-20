package com.ramiyon.soulmath.presentation.ui.material.onboard.screens.second

import android.graphics.Paint
import android.view.ViewGroup
import com.ramiyon.soulmath.base.BaseFragment
import com.ramiyon.soulmath.databinding.FragmentMaterialOnBoardSecondScreenBinding
import com.ramiyon.soulmath.util.ScreenOrientation

class MaterialOnBoardSecondScreenFragment : BaseFragment<FragmentMaterialOnBoardSecondScreenBinding>() {

    override fun inflateViewBinding(container: ViewGroup?): FragmentMaterialOnBoardSecondScreenBinding {
        return FragmentMaterialOnBoardSecondScreenBinding.inflate(layoutInflater, container, false)
    }

    override fun FragmentMaterialOnBoardSecondScreenBinding.binder() {
        tvLearningPurpose.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }


}