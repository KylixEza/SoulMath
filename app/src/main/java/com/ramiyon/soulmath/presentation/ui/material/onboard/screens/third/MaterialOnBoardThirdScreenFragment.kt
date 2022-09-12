package com.ramiyon.soulmath.presentation.ui.material.onboard.screens.third

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.ramiyon.soulmath.base.BaseFragment
import com.ramiyon.soulmath.databinding.FragmentMaterialOnBoardThirdScreenBinding
import com.ramiyon.soulmath.domain.model.material.MaterialOnBoard
import com.ramiyon.soulmath.presentation.ui.material.dashboard.MaterialDashboardActivity
import com.ramiyon.soulmath.presentation.ui.material.onboard.MaterialOnBoardViewModel
import com.ramiyon.soulmath.util.Constanta.ARG_MATERIAL_ID
import com.ramiyon.soulmath.util.Constanta.ARG_MODULE_ID
import com.ramiyon.soulmath.util.Constanta.ARG_MODULE_TITLE
import com.ramiyon.soulmath.util.Constanta.ARG_STRING_ARRAY_LIST
import com.ramiyon.soulmath.util.Resource
import com.ramiyon.soulmath.util.ResourceStateCallback
import com.ramiyon.soulmath.util.ScreenOrientation
import com.ramiyon.soulmath.util.callGlide
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MaterialOnBoardThirdScreenFragment : BaseFragment<FragmentMaterialOnBoardThirdScreenBinding>() {

    private val viewModel by sharedViewModel<MaterialOnBoardViewModel>()
    private var materialId: String? = null
    private var moduleId: String? = null
    private var moduleTitle: String? = null

    companion object {
        fun getInstance(materialId: String, moduleId: String, moduleTitle: String) = MaterialOnBoardThirdScreenFragment().apply {
            arguments = Bundle().apply {
                putStringArrayList(ARG_STRING_ARRAY_LIST, arrayListOf(materialId, moduleId, moduleTitle))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val argResult = arguments?.getStringArrayList(ARG_STRING_ARRAY_LIST)
        materialId = argResult?.get(0)
        moduleId = argResult?.get(1)
        moduleTitle = argResult?.get(2)
    }

    override fun inflateViewBinding(container: ViewGroup?): FragmentMaterialOnBoardThirdScreenBinding {
        return FragmentMaterialOnBoardThirdScreenBinding.inflate(layoutInflater, container, false)
    }

    override fun FragmentMaterialOnBoardThirdScreenBinding.binder() {
        viewModel.fetchMaterialOnBoardContentById(materialId!!, 3)

        lifecycleScope.launchWhenStarted {
            viewModel.contentState.collect {
                when(it) {
                    is Resource.Loading -> materialOnBoardThirdScreenResourceCallback.onResourceLoading()
                    is Resource.Success -> materialOnBoardThirdScreenResourceCallback.onResourceSuccess(it.data!!.first())
                    is Resource.Error -> materialOnBoardThirdScreenResourceCallback.onResourceError(it.message!!)
                    else -> materialOnBoardThirdScreenResourceCallback.onNeverFetched()
                }
            }
        }

        btnNextMaterialThirdOnboard.setOnClickListener {
            val intent = Intent(requireActivity(), MaterialDashboardActivity::class.java)
            intent.putExtra(ARG_MODULE_ID, moduleId)
            intent.putExtra(ARG_MODULE_TITLE, moduleTitle)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }

    private val materialOnBoardThirdScreenResourceCallback = object : ResourceStateCallback<MaterialOnBoard>() {
        override fun onResourceLoading() {

        }

        override fun onResourceSuccess(data: MaterialOnBoard) {
            lifecycleScope.launchWhenStarted {
                viewModel.content.collect {
                    binding?.apply {
                        callGlide(requireContext(), data.upperImage, ivUpperImageThirdOnboard)
                        callGlide(requireContext(), data.lowerImage, ivLowerImageThirdOnboard)

                        tvMaterialThirdOnboard.text = data.description
                    }
                }
            }
        }

    }
}