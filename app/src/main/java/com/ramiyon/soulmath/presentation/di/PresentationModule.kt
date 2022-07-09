package com.ramiyon.soulmath.presentation.di

import com.ramiyon.soulmath.presentation.adapter.LeaderboardAdapter
import com.ramiyon.soulmath.presentation.ui.auth.login.LoginViewModel
import com.ramiyon.soulmath.presentation.ui.auth.register.RegisterViewModel
import com.ramiyon.soulmath.presentation.ui.leaderboard.LeaderboardViewModel
import com.ramiyon.soulmath.presentation.ui.onboard.screens.first.FirstScreenViewModel
import com.ramiyon.soulmath.presentation.ui.onboard.screens.second.SecondScreenViewModel
import com.ramiyon.soulmath.presentation.ui.onboard.screens.third.ThirdScreenViewModel
import com.ramiyon.soulmath.presentation.ui.splash.SplashViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { FirstScreenViewModel() }
    viewModel { SecondScreenViewModel() }
    viewModel { ThirdScreenViewModel() }
    viewModel { SplashViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { LeaderboardViewModel(get()) }
}

val adapterModule = module {
    factory { LeaderboardAdapter(androidContext()) }
}