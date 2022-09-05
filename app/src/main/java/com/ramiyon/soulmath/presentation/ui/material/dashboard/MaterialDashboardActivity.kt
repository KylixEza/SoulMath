package com.ramiyon.soulmath.presentation.ui.material.dashboard

import androidx.recyclerview.widget.LinearLayoutManager
import com.gdsc.gdsctoast.GDSCToast.Companion.showAnyToast
import com.gdsc.gdsctoast.util.ToastShape
import com.gdsc.gdsctoast.util.ToastType
import com.ramiyon.soulmath.base.BaseActivity
import com.ramiyon.soulmath.databinding.ActivityMaterialDashboardBinding
import com.ramiyon.soulmath.domain.model.material.Material
import com.ramiyon.soulmath.presentation.adapter.MaterialAdapter
import com.ramiyon.soulmath.util.Constanta.ARG_MODULE_ID
import com.ramiyon.soulmath.util.Constanta.ARG_MODULE_TITLE
import com.ramiyon.soulmath.util.Resource
import com.ramiyon.soulmath.util.ResourceStateCallback
import com.ramiyon.soulmath.util.ScreenOrientation
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MaterialDashboardActivity : BaseActivity<ActivityMaterialDashboardBinding>() {

    private val viewModel by viewModel<MaterialDashboardViewModel>()
    private val adapter by inject<MaterialAdapter>()

    override fun inflateViewBinding(): ActivityMaterialDashboardBinding {
        return ActivityMaterialDashboardBinding.inflate(layoutInflater)
    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }

    override fun ActivityMaterialDashboardBinding.binder() {
        
        setSupportActionBar(materialDashboardToolbar)
        supportActionBar?.hide()
        
        val moduleId: String = intent.getStringExtra(ARG_MODULE_ID) ?: ""
        val moduleTitle = intent.getStringExtra(ARG_MODULE_TITLE) ?: ""
        
        tvToolbarTitle.text = moduleTitle

        rvMaterialDashboard.apply {
            adapter = this@MaterialDashboardActivity.adapter
            layoutManager = LinearLayoutManager(this@MaterialDashboardActivity, LinearLayoutManager.VERTICAL, false)
        }

        viewModel.fetchMaterials(moduleId).observe(this@MaterialDashboardActivity) {
            when(it) {
                is Resource.Loading -> materialsCallback.onResourceLoading()
                is Resource.Success -> materialsCallback.onResourceSuccess(it.data!!)
                is Resource.Error -> materialsCallback.onResourceError(it.message!!, null)
                is Resource.Empty -> materialsCallback.onNeverFetched()
            }
        }
    }

    private val materialsCallback = object : ResourceStateCallback<List<Material>>() {

        override fun onResourceLoading() {
            binding.apply {
                progressRvMaterialDashboard.visibility = visible
                rvMaterialDashboard.visibility = invisible
            }
        }

        override fun onResourceSuccess(data: List<Material>) {
            binding.apply {
                progressRvMaterialDashboard.visibility = invisible
                rvMaterialDashboard.visibility = visible
            }
            adapter.submitData(data)
        }

        override fun onResourceError(message: String?, data: List<Material>?) {
            this@MaterialDashboardActivity.apply { showAnyToast { it.apply {
                text = message.toString()
                toastType = ToastType.ERROR
                toastShape = ToastShape.RECTANGLE
            } } }
        }
    }
}