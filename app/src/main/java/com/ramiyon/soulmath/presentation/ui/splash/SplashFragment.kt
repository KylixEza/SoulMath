package com.ramiyon.soulmath.presentation.ui.splash

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.ramiyon.soulmath.R
import com.ramiyon.soulmath.base.BaseFragment
import com.ramiyon.soulmath.databinding.FragmentSplashBinding
import com.ramiyon.soulmath.util.Resource
import com.ramiyon.soulmath.util.ScreenOrientation
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    private val viewModel by viewModel<SplashViewModel>()

    override fun onCreateViewBehaviour(inflater: LayoutInflater, container: ViewGroup?) {
        requireActivity().window.statusBarColor = View.GONE
    }

    private fun observeHaveRunAppBefore(view: View) {
        viewModel.readPrefHaveRunAppBefore().observe(viewLifecycleOwner) { haveRun ->
            if (haveRun) {
                Log.d("decision", "remember me")
                observeRememberMe(view)
            } else {
                Log.d("decision", "on board")
                view.findNavController().navigate(
                    SplashFragmentDirections.actionSplashFragmentToOnBoardingFragment("Splash")
                )
            }
        }
    }

    private fun observeRememberMe(view: View) {
        viewModel.readPrefRememberMe().observe(viewLifecycleOwner) { isRemember ->
            if (isRemember) {
                viewModel.fetchStudentDetail().observe(viewLifecycleOwner) {
                    when(it) {
                        is Resource.Loading -> {}
                        is Resource.Success -> {
                            view.findNavController().navigate(R.id.action_splashFragment_to_baseActivity)
                            activity?.finish()
                        }
                        else -> {}
                    }
                }
            } else {
                view.findNavController().navigate(R.id.action_splash_destination_to_loginFragment)
            }
        }
    }

    override fun inflateViewBinding(container: ViewGroup?): FragmentSplashBinding {
        return FragmentSplashBinding.inflate(layoutInflater, container, false)
    }

    override fun FragmentSplashBinding.binder() {
        observeHaveRunAppBefore(fragmentView)
    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }

}