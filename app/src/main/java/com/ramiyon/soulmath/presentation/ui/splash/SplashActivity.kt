package com.ramiyon.soulmath.presentation.ui.splash

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.ramiyon.soulmath.R
import com.ramiyon.soulmath.presentation.ui.auth.register.RegisterFragmentDirections
import kotlinx.android.synthetic.main.activity_splash.*

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onBackPressed() {
        when(NavHostFragment.findNavController(splash_navigation.getFragment()).currentDestination?.id) {
            R.id.splash_destination -> finish()
            R.id.on_boarding_destination -> finish()
            R.id.login_destination -> finish()
            R.id.register_destination -> findNavController(R.id.splash_navigation).navigate(
                RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            )
        }
    }
}