package com.ramiyon.soulmath.presentation.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.ramiyon.soulmath.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : Fragment() {

    private val viewModel by viewModel<SplashViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().window.statusBarColor = View.GONE
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            observeHaveRunAppBefore(view)
        }, 2000L)
    }

    private fun observeHaveRunAppBefore(view: View) {
        viewModel.readPrefHaveRunAppBefore().observe(viewLifecycleOwner) { haveRun ->
            if (haveRun) {
                observeRememberMe(view)
            } else {
                view.findNavController().navigate(
                    SplashFragmentDirections.actionSplashFragmentToOnBoardingFragment("Splash")
                )
            }
        }
    }

    private fun observeRememberMe(view: View) {
        viewModel.readPrefRememberMe().observe(viewLifecycleOwner) { isRemember ->
            if (isRemember) {
                view.findNavController().navigate(R.id.action_splashFragment_to_baseActivity)
                activity?.finish()
            } else {
                view.findNavController().navigate(R.id.action_splash_destination_to_loginFragment)
            }
        }
    }

}