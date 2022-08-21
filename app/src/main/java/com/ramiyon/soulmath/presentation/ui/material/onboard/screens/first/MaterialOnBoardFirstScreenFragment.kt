package com.ramiyon.soulmath.presentation.ui.material.onboard.screens.first

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.ramiyon.soulmath.base.BaseFragment
import com.ramiyon.soulmath.databinding.ActivityMaterialOnBoardBinding
import com.ramiyon.soulmath.databinding.FragmentMaterialOnBoardFirstScreenBinding
import com.ramiyon.soulmath.util.Constanta.ARG_MATERIAL_ID
import com.ramiyon.soulmath.util.ScreenOrientation
import org.koin.androidx.viewmodel.ext.android.viewModel

class MaterialOnBoardFirstScreenFragment : BaseFragment<FragmentMaterialOnBoardFirstScreenBinding>() {

    private val viewModel by viewModel<MaterialOnBoardFirstScreenViewModel>()
    private lateinit var parentBinding: ActivityMaterialOnBoardBinding
    private var materialId: String? = null

    companion object {
        fun getInstance(moduleId: String) = MaterialOnBoardFirstScreenFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_MATERIAL_ID, moduleId)
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

    override fun inflateViewBinding(container: ViewGroup?): FragmentMaterialOnBoardFirstScreenBinding {
        return FragmentMaterialOnBoardFirstScreenBinding.inflate(layoutInflater, container, false)
    }

    override fun FragmentMaterialOnBoardFirstScreenBinding.binder() {
        val onBoardContent = viewModel.getMaterialOnBoardFirstScreen(materialId!!)

        Glide.with(this@MaterialOnBoardFirstScreenFragment)
            .load(onBoardContent.gif)
            .into(ivGifMaterialFirstOnboard)
        tvMaterialFirstOnboard.text = onBoardContent.description

        btnNextMaterialFirstOnboard.setOnClickListener {
            parentBinding.vpMaterialOnboard.currentItem = 1
        }
    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }

}