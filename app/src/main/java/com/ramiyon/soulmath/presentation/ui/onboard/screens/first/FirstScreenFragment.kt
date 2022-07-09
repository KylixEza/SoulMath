package com.ramiyon.soulmath.presentation.ui.onboard.screens.first

import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.ramiyon.soulmath.R
import com.ramiyon.soulmath.base.BaseFragment
import com.ramiyon.soulmath.databinding.FragmentFirstScreenBinding
import com.ramiyon.soulmath.presentation.ui.onboard.screens.OnBoardScreenViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FirstScreenFragment : BaseFragment<FragmentFirstScreenBinding>() {

    private val viewModel by sharedViewModel<OnBoardScreenViewModel>()

    override fun inflateViewBinding(container: ViewGroup?): FragmentFirstScreenBinding {
        return FragmentFirstScreenBinding.inflate(layoutInflater, container, false)
    }

    override fun FragmentFirstScreenBinding.binder() {
        val viewPager = activity?.findViewById<ViewPager2>(R.id.view_pager)
        btnNext.setOnClickListener {
            viewPager?.currentItem = 1
        }
        tvFirstOnboardTitle.text = viewModel.getOnBoardContentTitleByPage(0)
        tvFirstOnboardSubtitle.text = viewModel.getOnBoardContentSubtitleByPage(0)
    }
}