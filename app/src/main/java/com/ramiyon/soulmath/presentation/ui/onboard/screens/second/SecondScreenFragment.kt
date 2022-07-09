package com.ramiyon.soulmath.presentation.ui.onboard.screens.second

import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.ramiyon.soulmath.R
import com.ramiyon.soulmath.base.BaseFragment
import com.ramiyon.soulmath.databinding.FragmentSecondScreenBinding
import com.ramiyon.soulmath.util.ScreenOrientation
import org.koin.androidx.viewmodel.ext.android.viewModel

class SecondScreenFragment : BaseFragment<FragmentSecondScreenBinding>() {

    private val viewModel by viewModel<SecondScreenViewModel>()

    override fun inflateViewBinding(container: ViewGroup?): FragmentSecondScreenBinding {
        return FragmentSecondScreenBinding.inflate(layoutInflater, container, false)
    }

    override fun FragmentSecondScreenBinding.binder() {
        val viewPager = activity?.findViewById<ViewPager2>(R.id.view_pager)
        btnNext.setOnClickListener {
            viewPager?.currentItem = 2
        }
        tvSecondOnboardTitle.text = viewModel.title
        tvSecondOnboardSubtitle.text = viewModel.subtitle
    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }
}