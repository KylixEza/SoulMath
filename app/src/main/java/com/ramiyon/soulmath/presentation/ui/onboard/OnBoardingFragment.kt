package com.ramiyon.soulmath.presentation.ui.onboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.navArgs
import com.ramiyon.soulmath.base.BaseFragment
import com.ramiyon.soulmath.databinding.FragmentOnBoardingBinding
import com.ramiyon.soulmath.presentation.adapter.OnBoardingViewPagerAdapter
import com.ramiyon.soulmath.presentation.animation.DepthPageTransform
import com.ramiyon.soulmath.presentation.ui.onboard.screens.first.FirstScreenFragment
import com.ramiyon.soulmath.presentation.ui.onboard.screens.second.SecondScreenFragment
import com.ramiyon.soulmath.presentation.ui.onboard.screens.third.ThirdScreenFragment
import com.ramiyon.soulmath.util.ScreenOrientation

class OnBoardingFragment : BaseFragment<FragmentOnBoardingBinding>() {

    private val args by navArgs<OnBoardingFragmentArgs>()
    lateinit var argSource: String

    override fun onCreateViewBehaviour(inflater: LayoutInflater, container: ViewGroup?) {
        requireActivity().window.statusBarColor = View.GONE
    }

    override fun inflateViewBinding(container: ViewGroup?): FragmentOnBoardingBinding {
        return FragmentOnBoardingBinding.inflate(layoutInflater, container, false)
    }

    override fun FragmentOnBoardingBinding.binder() {
        argSource = args.source

        val listOfFragment = listOf(
            FirstScreenFragment(),
            SecondScreenFragment(),
            ThirdScreenFragment(),
        )

        val adapter = OnBoardingViewPagerAdapter(
            requireActivity().supportFragmentManager,
            lifecycle
        )

        adapter.apply {
            setAllFragments(listOfFragment)
            viewPager.adapter = this
            viewPager.setPageTransformer(DepthPageTransform())
        }
        pageIndicatorView.setViewPager2(viewPager)


        activity?.onBackPressedDispatcher?.addCallback {
            if (argSource == "Login" || argSource == "Register") {
                binding?.viewPager?.currentItem = 3
            } else {
                when(binding?.viewPager?.currentItem) {
                    0 -> activity?.finish()
                    1 -> binding?.viewPager?.currentItem = 0
                    2 -> binding?.viewPager?.currentItem = 1
                    3 -> binding?.viewPager?.currentItem = 2
                }
            }
        }
    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }
}