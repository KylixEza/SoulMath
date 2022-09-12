package com.ramiyon.soulmath.presentation.ui.material.onboard.screens.second

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.ramiyon.soulmath.base.BaseFragment
import com.ramiyon.soulmath.databinding.ActivityMaterialOnBoardBinding
import com.ramiyon.soulmath.databinding.FragmentMaterialOnBoardSecondScreenBinding
import com.ramiyon.soulmath.domain.model.material.MaterialOnBoard
import com.ramiyon.soulmath.presentation.ui.material.onboard.MaterialOnBoardActivity
import com.ramiyon.soulmath.presentation.ui.material.onboard.MaterialOnBoardViewModel
import com.ramiyon.soulmath.util.Constanta.ARG_MATERIAL_ID
import com.ramiyon.soulmath.util.Resource
import com.ramiyon.soulmath.util.ResourceStateCallback
import com.ramiyon.soulmath.util.ScreenOrientation
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MaterialOnBoardSecondScreenFragment : BaseFragment<FragmentMaterialOnBoardSecondScreenBinding>() {

    private val viewModel by sharedViewModel<MaterialOnBoardViewModel>()
    private lateinit var parent: ActivityMaterialOnBoardBinding
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

    override fun inflateViewBinding(container: ViewGroup?): FragmentMaterialOnBoardSecondScreenBinding {
        return FragmentMaterialOnBoardSecondScreenBinding.inflate(layoutInflater, container, false)
    }

    override fun FragmentMaterialOnBoardSecondScreenBinding.binder() {
        parent = (requireActivity() as MaterialOnBoardActivity).binding
        
        tvLearningPurpose.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        viewModel.fetchMaterialOnBoardContentById(materialId!!, 2)

        lifecycleScope.launchWhenStarted {
            viewModel.contentState.collect {
                when(it) {
                    is Resource.Loading -> materialOnBoardSecondScreenStateCallback.onResourceLoading()
                    is Resource.Success -> materialOnBoardSecondScreenStateCallback.onResourceSuccess(it.data!!.first())
                    is Resource.Error -> materialOnBoardSecondScreenStateCallback.onResourceError(it.message!!)
                    else -> materialOnBoardSecondScreenStateCallback.onNeverFetched()
                }
            }
        }

        tvLearningPurpose.setOnClickListener {
            val bottomSheetFragment = MaterialLearningPurposeBottomSheetFragment.getInstance(materialId!!)
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
        }

        btnNextMaterialSecondOnboard.setOnClickListener {
            parent.vpMaterialOnboard.currentItem = 2
        }
    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }

    private val materialOnBoardSecondScreenStateCallback = object : ResourceStateCallback<MaterialOnBoard>() {
        override fun onResourceLoading() {
        
        }
    
        override fun onResourceSuccess(data: MaterialOnBoard) {
            lifecycleScope.launchWhenStarted {
                viewModel.content.collect {
                    binding?.apply {
                        Glide.with(this@MaterialOnBoardSecondScreenFragment)
                            .load(it.gif)
                            .into(ivGifMaterialSecondOnboard)
                        tvMaterialSecondOnboard.text = it.description
                    }
                }
            }
        }
    
    }

}