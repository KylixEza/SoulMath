package com.ramiyon.soulmath.presentation.ui.onboard.screens.third

import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.ramiyon.soulmath.R
import com.ramiyon.soulmath.base.BaseFragment
import com.ramiyon.soulmath.databinding.FragmentThirdScreenBinding
import com.ramiyon.soulmath.presentation.ui.onboard.screens.OnBoardScreenViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ThirdScreenFragment : BaseFragment<FragmentThirdScreenBinding>() {

    private val viewModel by sharedViewModel<OnBoardScreenViewModel>()

    override fun inflateViewBinding(container: ViewGroup?): FragmentThirdScreenBinding {
        return FragmentThirdScreenBinding.inflate(layoutInflater, container, false)
    }

    override fun FragmentThirdScreenBinding.binder() {
        val viewPager = activity?.findViewById<ViewPager2>(R.id.view_pager)
        btnNext.setOnClickListener {
            viewPager?.currentItem = 1
        }
        tvThirdOnboardTitle.text = viewModel.getOnBoardContentTitleByPage(2)
        tvThirdOnboardSubtitle.text = viewModel.getOnBoardContentSubtitleByPage(2)
    }
}