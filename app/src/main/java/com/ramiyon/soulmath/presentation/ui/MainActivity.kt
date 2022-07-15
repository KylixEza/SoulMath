package com.ramiyon.soulmath.presentation.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavHost
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.plusAssign
import androidx.navigation.ui.setupWithNavController
import com.ramiyon.soulmath.R
import com.ramiyon.soulmath.SoulMathLifecycleObserver
import com.ramiyon.soulmath.databinding.ActivityMainBinding
import com.ramiyon.soulmath.presentation.navigation.KeepStateNavigator
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val lifecycleObserver: SoulMathLifecycleObserver by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = resources.getColor(R.color.white)
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpNavigation()

        lifecycle.addObserver(lifecycleObserver)
    }

    private fun setUpNavigation() {
        val navController = findNavController(R.id.main_navigation)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_navigation)!!
        val navigator = KeepStateNavigator(this, navHostFragment.childFragmentManager, R.id.main_navigation)
        navController.navigatorProvider += navigator
        navController.setGraph(R.navigation.mobile_navigation)
        binding.mainBottomNav.setupWithNavController(navController)
    }
}