package com.ramiyon.soulmath.presentation.di

import com.ramiyon.soulmath.presentation.adapter.*
import com.ramiyon.soulmath.presentation.ui.auth.login.LoginViewModel
import com.ramiyon.soulmath.presentation.ui.auth.register.RegisterViewModel
import com.ramiyon.soulmath.presentation.ui.home.HomeViewModel
import com.ramiyon.soulmath.presentation.ui.leaderboard.LeaderboardViewModel
import com.ramiyon.soulmath.presentation.ui.material.dashboard.MaterialDashboardViewModel
import com.ramiyon.soulmath.presentation.ui.material.onboard.MaterialOnBoardViewModel
import com.ramiyon.soulmath.presentation.ui.material.reward.MaterialRewardViewModel
import com.ramiyon.soulmath.presentation.ui.material.video.MaterialVideoPlayerViewModel
import com.ramiyon.soulmath.presentation.ui.onboard.screens.first.FirstScreenViewModel
import com.ramiyon.soulmath.presentation.ui.onboard.screens.second.SecondScreenViewModel
import com.ramiyon.soulmath.presentation.ui.onboard.screens.third.ThirdScreenViewModel
import com.ramiyon.soulmath.presentation.ui.profile.ProfileViewModel
import com.ramiyon.soulmath.presentation.ui.splash.SplashViewModel
import com.ramiyon.soulmath.presentation.validator.LoginValidator
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { FirstScreenViewModel(get()) }
    viewModel { SecondScreenViewModel(get()) }
    viewModel { ThirdScreenViewModel(get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { LeaderboardViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { MaterialOnBoardViewModel(get()) }
    viewModel { MaterialDashboardViewModel(get()) }
    viewModel { MaterialVideoPlayerViewModel(get()) }
    viewModel { MaterialRewardViewModel(get()) }
}

val adapterModule = module {
    factory { LeaderboardAdapter() }
    factory { DailyXpAdapter() }
    factory { ProfileAddOnAdapter() }
    factory { LearningJourneyAdapter() }
    factory { LearningPurposeAdapter() }
    factory { MaterialAdapter() }
}

val validatorModule = module {
    factory { LoginValidator() }
}