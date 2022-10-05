package com.ramiyon.soulmath.presentation.ui.game.easy

import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.ramiyon.soulmath.base.BaseActivity
import com.ramiyon.soulmath.databinding.ActivityEasyGameBinding
import com.ramiyon.soulmath.domain.model.Question
import com.ramiyon.soulmath.util.Constanta.ARG_GAME_ID
import com.ramiyon.soulmath.util.Resource
import com.ramiyon.soulmath.util.ResourceStateCallback
import com.ramiyon.soulmath.util.ScreenOrientation
import com.ramiyon.soulmath.util.callGlide
import org.koin.androidx.viewmodel.ext.android.viewModel

class EasyGameActivity : BaseActivity<ActivityEasyGameBinding>() {

    private val viewModel by viewModel<EasyGameViewModel>()

    override fun inflateViewBinding(): ActivityEasyGameBinding = ActivityEasyGameBinding.inflate(layoutInflater)

    override fun determineScreenOrientation(): ScreenOrientation = ScreenOrientation.LANDSCAPE

    override fun ActivityEasyGameBinding.binder() {

        val gameId = intent.getStringExtra(ARG_GAME_ID) ?: ""

        viewModel.fetchQuestions(gameId).observe(this@EasyGameActivity) { resource ->
            when(resource) {
                is Resource.Empty -> easyGameQuestions.onNeverFetched()
                is Resource.Error -> easyGameQuestions.onResourceError(resource.message!!)
                is Resource.Loading -> easyGameQuestions.onResourceLoading()
                is Resource.Success -> easyGameQuestions.onResourceSuccess(resource.data!!)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.question.collect { question ->
                binding.apply {
                    tvQuestion?.text = question?.question
                    Glide.with(this@EasyGameActivity).load(question?.questionImage).into(ivQuestion!!)

                }
            }
        }
    }

    private val ActivityEasyGameBinding.easyGameQuestions: ResourceStateCallback<List<Question>>
        get() = object : ResourceStateCallback<List<Question>>() {
            override fun onResourceLoading() {

            }

            override fun onResourceSuccess(data: List<Question>) {
                viewModel.setQuestion(data[0])
            }
        }



}