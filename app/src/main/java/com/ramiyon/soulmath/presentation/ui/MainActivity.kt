package com.ramiyon.soulmath.presentation.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ramiyon.soulmath.R
import com.ramiyon.soulmath.SoulMathLifecycleObserver
import com.ramiyon.soulmath.databinding.ActivityMainBinding
import com.ramiyon.soulmath.presentation.ui.leaderboard.LeaderboardFragmentDirections
import com.ramiyon.soulmath.presentation.ui.profile.ProfileFragmentDirections
import kotlinx.android.synthetic.main.activity_main.*
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
        binding.mainBottomNav.setupWithNavController(navController)
    }
    
    override fun onBackPressed() {
        when(NavHostFragment.findNavController(main_navigation).currentDestination?.id) {
            R.id.home_destination -> finish()
            R.id.leaderboard_destination -> NavHostFragment.findNavController(main_navigation).navigate(LeaderboardFragmentDirections.actionLeaderboardDestinationToHomeDestination())
            R.id.profile_destination -> NavHostFragment.findNavController(main_navigation).navigate(ProfileFragmentDirections.actionProfileDestinationToHomeDestination())
            else -> super.onBackPressed()
        }
    }
}