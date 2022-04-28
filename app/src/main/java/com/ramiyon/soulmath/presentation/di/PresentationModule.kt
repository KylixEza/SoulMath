package com.ramiyon.soulmath.presentation.di

import com.ramiyon.soulmath.presentation.auth.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
}