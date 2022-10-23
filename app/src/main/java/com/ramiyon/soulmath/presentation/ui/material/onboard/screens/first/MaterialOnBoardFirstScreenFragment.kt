package com.ramiyon.soulmath.presentation.ui.material.onboard.screens.first

import android.os.Bundle
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.ramiyon.soulmath.base.BaseFragment
import com.ramiyon.soulmath.databinding.ActivityMaterialOnBoardBinding
import com.ramiyon.soulmath.databinding.DialogLottieBinding
import com.ramiyon.soulmath.databinding.FragmentMaterialOnBoardFirstScreenBinding
import com.ramiyon.soulmath.domain.model.material.MaterialOnBoard
import com.ramiyon.soulmath.presentation.common.buildLottieDialog
import com.ramiyon.soulmath.presentation.ui.material.onboard.MaterialOnBoardActivity
import com.ramiyon.soulmath.presentation.ui.material.onboard.MaterialOnBoardViewModel
import com.ramiyon.soulmath.util.Constanta.ARG_MATERIAL_ID
import com.ramiyon.soulmath.util.Resource
import com.ramiyon.soulmath.util.ResourceStateCallback
import com.ramiyon.soulmath.util.ScreenOrientation
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MaterialOnBoardFirstScreenFragment : BaseFragment<FragmentMaterialOnBoardFirstScreenBinding>() {

    private val viewModel by sharedViewModel<MaterialOnBoardViewModel>()
    private lateinit var lottieBinding: DialogLottieBinding
    private lateinit var parent: ActivityMaterialOnBoardBinding
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
    
    override fun inflateViewBinding(container: ViewGroup?): FragmentMaterialOnBoardFirstScreenBinding {
        return FragmentMaterialOnBoardFirstScreenBinding.inflate(layoutInflater, container, false)
    }

    override fun FragmentMaterialOnBoardFirstScreenBinding.binder() {
        lottieBinding = DialogLottieBinding.inflate(layoutInflater)
        val content = viewModel.getDummyMaterialOnBoardContent(materialId!!, 1)

        binding?.apply {
            Glide.with(this@MaterialOnBoardFirstScreenFragment)
                .load(content.gif)
                .into(ivGifMaterialFirstOnboard)
            tvMaterialFirstOnboard.text = content.description
        }

        /*lifecycleScope.launchWhenStarted {
            viewModel.contentState.collect { resource ->
                when(resource) {
                    is Resource.Success -> materialOnBoardFirstScreenStateCallback.onResourceSuccess(resource.data!!.first())
                    is Resource.Error -> materialOnBoardFirstScreenStateCallback.onResourceError(resource.message!!)
                    is Resource.Loading -> materialOnBoardFirstScreenStateCallback.onResourceLoading()
                    else -> materialOnBoardFirstScreenStateCallback.onNeverFetched()
                }
            }
        }*/

        btnNextMaterialFirstOnboard.setOnClickListener {
            parent.vpMaterialOnboard.currentItem = 1
        }
        
        parent = (activity as MaterialOnBoardActivity).binding
    }

    private val materialOnBoardFirstScreenStateCallback = object : ResourceStateCallback<MaterialOnBoard>() {
        val lottieDialog = activity?.buildLottieDialog(lottieBinding, "loading_blue_paper_airplane.json")

        override fun onResourceLoading() {
            lottieDialog?.show()
        }

        override fun onResourceSuccess(data: MaterialOnBoard) {
            lifecycleScope.launchWhenStarted {
                viewModel.content.collect {
                    lottieDialog?.hide()
                    binding?.apply {
                        Glide.with(this@MaterialOnBoardFirstScreenFragment)
                            .load(it.gif)
                            .into(ivGifMaterialFirstOnboard)
                        tvMaterialFirstOnboard.text = it.description

                    }
                }
            }
        }

        override fun onResourceError(message: String) {
            lottieDialog?.hide()
        }
    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }

}