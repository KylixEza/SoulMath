package com.ramiyon.soulmath.presentation.ui.material.onboard.screens.third

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.ramiyon.soulmath.base.BaseFragment
import com.ramiyon.soulmath.databinding.FragmentMaterialOnBoardThirdScreenBinding
import com.ramiyon.soulmath.presentation.ui.material.dashboard.MaterialDashboardActivity
import com.ramiyon.soulmath.util.Constanta.ARG_MATERIAL_ID
import com.ramiyon.soulmath.util.ScreenOrientation
import org.koin.androidx.viewmodel.ext.android.viewModel

class MaterialOnBoardThirdScreenFragment : BaseFragment<FragmentMaterialOnBoardThirdScreenBinding>() {

    private val viewModel by viewModel<MaterialOnBoardThirdScreenViewModel>()
    private var materialId: String? = null

    companion object {
        fun getInstance(materialId: String) = MaterialOnBoardThirdScreenFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_MATERIAL_ID, materialId)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        materialId = arguments?.getString(ARG_MATERIAL_ID)
    }

    override fun inflateViewBinding(container: ViewGroup?): FragmentMaterialOnBoardThirdScreenBinding {
        return FragmentMaterialOnBoardThirdScreenBinding.inflate(layoutInflater, container, false)
    }

    override fun FragmentMaterialOnBoardThirdScreenBinding.binder() {
        val onBoardContent = viewModel.getMaterialOnBoardThirdScreen(materialId!!)

        Glide.with(this@MaterialOnBoardThirdScreenFragment)
            .load(onBoardContent.upperImage)
            .into(ivUpperImageThirdOnboard)

        Glide.with(this@MaterialOnBoardThirdScreenFragment)
            .load(onBoardContent.lowerImage)
            .into(ivLowerImageThirdOnboard)

        tvMaterialThirdOnboard.text = onBoardContent.description

        btnNextMaterialThirdOnboard.setOnClickListener {
            val intent = Intent(requireActivity(), MaterialDashboardActivity::class.java)

        }
    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }


}