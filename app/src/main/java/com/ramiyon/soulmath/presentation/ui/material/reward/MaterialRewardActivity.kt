package com.ramiyon.soulmath.presentation.ui.material.reward

import android.app.Dialog
import android.content.Intent
import com.ramiyon.soulmath.R
import com.ramiyon.soulmath.base.BaseActivity
import com.ramiyon.soulmath.databinding.ActivityMaterialRewardBinding
import com.ramiyon.soulmath.databinding.DialogLottieBinding
import com.ramiyon.soulmath.presentation.common.buildLottieDialog
import com.ramiyon.soulmath.presentation.ui.MainActivity
import com.ramiyon.soulmath.util.Constanta.ARG_MATERIAL_ID
import com.ramiyon.soulmath.util.Constanta.ARG_XP
import com.ramiyon.soulmath.util.Resource
import com.ramiyon.soulmath.util.ResourceStateCallback
import com.ramiyon.soulmath.util.ScreenOrientation
import org.koin.androidx.viewmodel.ext.android.viewModel

class MaterialRewardActivity : BaseActivity<ActivityMaterialRewardBinding>() {

    private val viewModel by viewModel<MaterialRewardViewModel>()
    private lateinit var lottieBinding: DialogLottieBinding
    private lateinit var lottieDialog: Dialog
    private lateinit var materialId: String

    override fun inflateViewBinding(): ActivityMaterialRewardBinding {
        return ActivityMaterialRewardBinding.inflate(layoutInflater)
    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }

    override fun ActivityMaterialRewardBinding.binder() {
        materialId = intent.getStringExtra(ARG_MATERIAL_ID) ?: ""

        lottieBinding = DialogLottieBinding.inflate(layoutInflater)
        lottieDialog = buildLottieDialog(lottieBinding, "loading_blue_paper_airplane.json")

        val xpEarned = intent.getIntExtra(ARG_XP, 0)
        tvXp.text = resources.getString(R.string.xp_earned, xpEarned)
        tvCongratulation.text = resources.getString(R.string.xp_earned_greet, xpEarned)
        viewModel.increaseStudentXp(xpEarned).observe(this@MaterialRewardActivity) {
            when(it) {
                is Resource.Loading -> rewardCallback.onResourceLoading()
                is Resource.Success -> rewardCallback.onResourceSuccess(Unit)
                is Resource.Error -> rewardCallback.onResourceError(it.message, null)
                is Resource.Empty -> rewardCallback.onNeverFetched()
            }
        }


        btnNext.setOnClickListener {
            val intent = Intent(this@MaterialRewardActivity, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }

    private val rewardCallback = object : ResourceStateCallback<Unit>() {
        override fun onResourceLoading() {
            lottieDialog.show()
        }

        override fun onResourceSuccess(data: Unit) {
            viewModel.unlockMaterial(materialId).observe(this@MaterialRewardActivity) {
                when(it) {
                    is Resource.Loading -> unlockCallback.onResourceLoading()
                    is Resource.Success -> unlockCallback.onResourceSuccess(Unit)
                    is Resource.Error -> unlockCallback.onResourceError(it.message, null)
                    is Resource.Empty -> unlockCallback.onNeverFetched()
                }
            }
        }

        override fun onResourceError(message: String?, data: Unit?) {
            lottieDialog.dismiss()
        }
    }

    private val unlockCallback = object : ResourceStateCallback<Unit>() {
        override fun onResourceLoading() {

        }

        override fun onResourceSuccess(data: Unit) {
            lottieDialog.dismiss()
        }

        override fun onResourceError(message: String?, data: Unit?) {
            lottieDialog.dismiss()
        }
    }
}