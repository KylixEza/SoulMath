package com.ramiyon.soulmath.presentation.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.ramiyon.soulmath.R
import com.ramiyon.soulmath.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
        observeHaveRunAppBefore(view)
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

}