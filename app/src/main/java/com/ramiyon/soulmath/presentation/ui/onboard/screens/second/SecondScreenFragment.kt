package com.ramiyon.soulmath.presentation.ui.onboard.screens.second

import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.ramiyon.soulmath.R
import com.ramiyon.soulmath.base.BaseFragment
import com.ramiyon.soulmath.databinding.FragmentSecondScreenBinding
import com.ramiyon.soulmath.presentation.ui.onboard.screens.OnBoardScreenViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SecondScreenFragment : BaseFragment<FragmentSecondScreenBinding>() {

    private val viewModel by sharedViewModel<OnBoardScreenViewModel>()

    override fun inflateViewBinding(container: ViewGroup?): FragmentSecondScreenBinding {
        return FragmentSecondScreenBinding.inflate(layoutInflater, container, false)
    }

    override fun FragmentSecondScreenBinding.binder() {
        val viewPager = activity?.findViewById<ViewPager2>(R.id.view_pager)
        btnNext.setOnClickListener {
            viewPager?.currentItem = 2
        }
        tvSecondOnboardTitle.text = viewModel.getOnBoardContentTitleByPage(1)
        tvSecondOnboardSubtitle.text = viewModel.getOnBoardContentSubtitleByPage(1)
    }
}