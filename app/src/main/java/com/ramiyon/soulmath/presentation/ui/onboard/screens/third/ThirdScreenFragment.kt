package com.ramiyon.soulmath.presentation.ui.onboard.screens.third

import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.ramiyon.soulmath.R
import com.ramiyon.soulmath.base.BaseFragment
import com.ramiyon.soulmath.databinding.FragmentThirdScreenBinding
import com.ramiyon.soulmath.presentation.ui.onboard.OnBoardingFragmentDirections
import com.ramiyon.soulmath.util.ScreenOrientation
import org.koin.androidx.viewmodel.ext.android.viewModel

class ThirdScreenFragment : BaseFragment<FragmentThirdScreenBinding>() {

    private val viewModel by viewModel<ThirdScreenViewModel>()

    override fun inflateViewBinding(container: ViewGroup?): FragmentThirdScreenBinding {
        return FragmentThirdScreenBinding.inflate(layoutInflater, container, false)
    }

    override fun FragmentThirdScreenBinding.binder() {
        btnNext.setOnClickListener {
            viewModel.savePrefHaveRunAppBefore(true)
            view?.findNavController()?.navigate(
                OnBoardingFragmentDirections.actionOnBoardingDestinationToLoginFragment()
            )
        }
        tvThirdOnboardTitle.text = viewModel.title
        tvThirdOnboardSubtitle.text = viewModel.subtitle
    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }
}