package com.ramiyon.soulmath.presentation.ui.game.hard

import com.ramiyon.soulmath.base.BaseActivity
import com.ramiyon.soulmath.databinding.ActivityHardGameBinding
import com.ramiyon.soulmath.domain.model.Question
import com.ramiyon.soulmath.util.Constanta.ARG_GAME_ID
import com.ramiyon.soulmath.util.Resource
import com.ramiyon.soulmath.util.ResourceStateCallback
import com.ramiyon.soulmath.util.ScreenOrientation
import org.koin.androidx.viewmodel.ext.android.viewModel

class HardGameActivity : BaseActivity<ActivityHardGameBinding>() {

    private val viewModel by viewModel<HardGameViewModel>()

    override fun inflateViewBinding(): ActivityHardGameBinding =
        ActivityHardGameBinding.inflate(layoutInflater)

    override fun determineScreenOrientation(): ScreenOrientation = ScreenOrientation.LANDSCAPE

    override fun ActivityHardGameBinding.binder() {

        val gameId = intent.getStringExtra(ARG_GAME_ID) ?: ""

        viewModel.fetchQuestions(gameId).observe(this@HardGameActivity) { resource ->
            when(resource) {
                is Resource.Empty -> hardGameCallback.onNeverFetched()
                is Resource.Error -> hardGameCallback.onResourceError(resource.message!!)
                is Resource.Loading -> hardGameCallback.onResourceLoading()
                is Resource.Success -> hardGameCallback.onResourceSuccess(resource.data!!)
            }
        }
    }

    private val ActivityHardGameBinding.hardGameCallback: ResourceStateCallback<List<Question>>
        get() = object : ResourceStateCallback<List<Question>>() {
            override fun onResourceLoading() {

            }

            override fun onResourceSuccess(data: List<Question>) {

            }
        }

}