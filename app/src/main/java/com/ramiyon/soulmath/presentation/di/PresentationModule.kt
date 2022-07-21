package com.ramiyon.soulmath.presentation.di

import com.ramiyon.soulmath.presentation.adapter.DailyXpAdapter
import com.ramiyon.soulmath.presentation.adapter.LeaderboardAdapter
import com.ramiyon.soulmath.presentation.adapter.ProfileAddOnAdapter
import com.ramiyon.soulmath.presentation.ui.auth.login.LoginViewModel
import com.ramiyon.soulmath.presentation.ui.auth.register.RegisterViewModel
import com.ramiyon.soulmath.presentation.ui.leaderboard.LeaderboardViewModel
import com.ramiyon.soulmath.presentation.ui.material.onboard.screens.first.MaterialOnBoardFirstScreenViewModel
import com.ramiyon.soulmath.presentation.ui.material.onboard.screens.second.MaterialOnBoardSecondScreenViewModel
import com.ramiyon.soulmath.presentation.ui.material.onboard.screens.third.MaterialOnBoardThirdScreenViewModel
import com.ramiyon.soulmath.presentation.ui.onboard.screens.first.FirstScreenViewModel
import com.ramiyon.soulmath.presentation.ui.onboard.screens.second.SecondScreenViewModel
import com.ramiyon.soulmath.presentation.ui.onboard.screens.third.ThirdScreenViewModel
import com.ramiyon.soulmath.presentation.ui.profile.ProfileViewModel
import com.ramiyon.soulmath.presentation.ui.splash.SplashViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { FirstScreenViewModel(get()) }
    viewModel { SecondScreenViewModel(get()) }
    viewModel { ThirdScreenViewModel(get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { LeaderboardViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { MaterialOnBoardFirstScreenViewModel(get()) }
    viewModel { MaterialOnBoardSecondScreenViewModel(get()) }
    viewModel { MaterialOnBoardThirdScreenViewModel(get()) }
}

val adapterModule = module {
    factory { LeaderboardAdapter(androidContext()) }
    factory { DailyXpAdapter(androidContext()) }
    factory { ProfileAddOnAdapter(androidContext()) }
}