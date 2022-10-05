package com.ramiyon.soulmath.presentation.ui.game.medium

import com.ramiyon.soulmath.base.BaseActivity
import com.ramiyon.soulmath.databinding.ActivityMediumGameBinding
import com.ramiyon.soulmath.domain.model.Question
import com.ramiyon.soulmath.util.Constanta.ARG_GAME_ID
import com.ramiyon.soulmath.util.Resource
import com.ramiyon.soulmath.util.ResourceStateCallback
import com.ramiyon.soulmath.util.ScreenOrientation
import org.koin.androidx.viewmodel.ext.android.viewModel

class MediumGameActivity : BaseActivity<ActivityMediumGameBinding>() {

    private val viewModel by viewModel<MediumGameViewModel>()

    override fun inflateViewBinding(): ActivityMediumGameBinding = ActivityMediumGameBinding.inflate(layoutInflater)

    override fun determineScreenOrientation(): ScreenOrientation = ScreenOrientation.LANDSCAPE

    override fun ActivityMediumGameBinding.binder() {
        val gameId = intent.getStringExtra(ARG_GAME_ID) ?: ""

        viewModel.fetchQuestions(gameId).observe(this@MediumGameActivity) { resource ->
            when(resource) {
                is Resource.Empty -> mediumGameCallback.onNeverFetched()
                is Resource.Error -> mediumGameCallback.onResourceError(resource.message!!)
                is Resource.Loading -> mediumGameCallback.onResourceLoading()
                is Resource.Success -> mediumGameCallback.onResourceSuccess(resource.data!!)
            }
        }
    }

    private val ActivityMediumGameBinding.mediumGameCallback: ResourceStateCallback<List<Question>>
        get() = object:  ResourceStateCallback<List<Question>>() {
            override fun onResourceLoading() {
                TODO("Not yet implemented")
            }

            override fun onResourceSuccess(data: List<Question>) {
                TODO("Not yet implemented")
            }
        }
}