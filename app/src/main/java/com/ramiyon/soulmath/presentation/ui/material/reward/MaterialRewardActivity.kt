package com.ramiyon.soulmath.presentation.ui.material.reward

import android.app.Dialog
import android.util.Log
import android.widget.Toast
import com.ramiyon.soulmath.base.BaseActivity
import com.ramiyon.soulmath.databinding.ActivityMaterialRewardBinding
import com.ramiyon.soulmath.databinding.DialogLottieBinding
import com.ramiyon.soulmath.presentation.common.buildLottieDialog
import com.ramiyon.soulmath.util.Constanta.ARG_XP
import com.ramiyon.soulmath.util.Resource
import com.ramiyon.soulmath.util.ResourceStateCallback
import com.ramiyon.soulmath.util.ScreenOrientation
import org.koin.androidx.viewmodel.ext.android.viewModel

class MaterialRewardActivity : BaseActivity<ActivityMaterialRewardBinding>() {

    private val viewModel by viewModel<MaterialRewardViewModel>()
    private lateinit var lottieBinding: DialogLottieBinding
    private lateinit var lottieDialog: Dialog

    override fun inflateViewBinding(): ActivityMaterialRewardBinding {
        return ActivityMaterialRewardBinding.inflate(layoutInflater)
    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }

    override fun ActivityMaterialRewardBinding.binder() {
        lottieBinding = DialogLottieBinding.inflate(layoutInflater)
        lottieDialog = buildLottieDialog(lottieBinding, "loading_blue_paper_airplane.json")

        val xpEarned = intent.getIntExtra(ARG_XP, 0)
        viewModel.increaseStudentXp(xpEarned).observe(this@MaterialRewardActivity) {
            when(it) {
                is Resource.Loading -> rewardCallback.onResourceLoading()
                is Resource.Success -> rewardCallback.onResourceSuccess(Unit)
                is Resource.Error -> rewardCallback.onResourceError(it.message, null)
                is Resource.Empty -> rewardCallback.onNeverFetched()
            }
        }
    }

    private val rewardCallback = object : ResourceStateCallback<Unit>() {
        override fun onResourceLoading() {
            lottieDialog.show()
        }

        override fun onResourceSuccess(data: Unit) {
            lottieDialog.dismiss()
        }

        override fun onResourceError(message: String?, data: Unit?) {
            lottieDialog.dismiss()
        }
    }
}