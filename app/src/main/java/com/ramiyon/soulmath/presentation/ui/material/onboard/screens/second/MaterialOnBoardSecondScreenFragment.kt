package com.ramiyon.soulmath.presentation.ui.material.onboard.screens.second

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.ramiyon.soulmath.base.BaseFragment
import com.ramiyon.soulmath.databinding.ActivityMaterialOnBoardBinding
import com.ramiyon.soulmath.databinding.FragmentMaterialOnBoardSecondScreenBinding
import com.ramiyon.soulmath.util.Constanta.ARG_MATERIAL_ID
import com.ramiyon.soulmath.util.ScreenOrientation
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MaterialOnBoardSecondScreenFragment : BaseFragment<FragmentMaterialOnBoardSecondScreenBinding>() {

    private val viewModel by sharedViewModel<MaterialOnBoardSecondScreenViewModel>()
    private lateinit var parentBinding: ActivityMaterialOnBoardBinding
    private var materialId: String? = null

    companion object {
        fun getInstance(materialId: String) = MaterialOnBoardSecondScreenFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_MATERIAL_ID, materialId)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            materialId = it.getString(ARG_MATERIAL_ID)
        }
    }

    override fun onCreateViewBehaviour(inflater: LayoutInflater, container: ViewGroup?) {
        parentBinding = ActivityMaterialOnBoardBinding.inflate(inflater, container, false)
    }

    override fun inflateViewBinding(container: ViewGroup?): FragmentMaterialOnBoardSecondScreenBinding {
        return FragmentMaterialOnBoardSecondScreenBinding.inflate(layoutInflater, container, false)
    }

    override fun FragmentMaterialOnBoardSecondScreenBinding.binder() {
        tvLearningPurpose.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        val onBoardContent = viewModel.getMaterialOnBoardSecondScreen(materialId!!)
        Glide.with(this@MaterialOnBoardSecondScreenFragment)
            .load(onBoardContent.gif)
            .into(ivGifMaterialSecondOnboard)
        tvMaterialSecondOnboard.text = onBoardContent.description

        tvLearningPurpose.setOnClickListener {
            val bottomSheetFragment = MaterialLearningPurposeBottomSheetFragment.getInstance(materialId!!)
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
        }

        btnNextMaterialSecondOnboard.setOnClickListener {
            parentBinding.vpMaterialOnboard.currentItem = 2
        }
    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }


}