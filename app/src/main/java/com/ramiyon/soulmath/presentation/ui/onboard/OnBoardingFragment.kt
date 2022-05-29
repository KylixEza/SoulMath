package com.ramiyon.soulmath.presentation.ui.onboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.navArgs
import com.ramiyon.soulmath.databinding.FragmentOnBoardingBinding
import com.ramiyon.soulmath.presentation.adapter.OnBoardingViewPagerAdapter
import com.ramiyon.soulmath.presentation.animation.DepthPageTransform
import com.ramiyon.soulmath.presentation.ui.onboard.screens.FirstScreenFragment
import com.ramiyon.soulmath.presentation.ui.onboard.screens.FourthScreenFragment
import com.ramiyon.soulmath.presentation.ui.onboard.screens.SecondScreenFragment
import com.ramiyon.soulmath.presentation.ui.onboard.screens.ThirdScreenFragment

class OnBoardingFragment : Fragment() {

    private var _binding: FragmentOnBoardingBinding? = null
    private val binding: FragmentOnBoardingBinding? get() = _binding
    private val args by navArgs<OnBoardingFragmentArgs>()
    lateinit var argSource: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().window.statusBarColor = View.GONE
        _binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        argSource = args.source

        val listOfFragment = listOf(
            FirstScreenFragment(),
            SecondScreenFragment(),
            ThirdScreenFragment(),
            FourthScreenFragment()
        )

        val adapter = OnBoardingViewPagerAdapter(
            requireActivity().supportFragmentManager,
            lifecycle
        )

        adapter.apply {
            setAllFragments(listOfFragment)
            binding?.viewPager?.adapter = this
            binding?.viewPager?.setPageTransformer(DepthPageTransform())
        }

        binding?.apply {
            pageIndicatorView.setViewPager2(viewPager)
        }

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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}