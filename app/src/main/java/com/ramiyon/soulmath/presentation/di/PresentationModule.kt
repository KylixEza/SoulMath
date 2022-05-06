package com.ramiyon.soulmath.presentation.di

import com.ramiyon.soulmath.presentation.ui.auth.login.LoginViewModel
import com.ramiyon.soulmath.presentation.ui.auth.register.RegisterViewModel
import com.ramiyon.soulmath.presentation.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
}