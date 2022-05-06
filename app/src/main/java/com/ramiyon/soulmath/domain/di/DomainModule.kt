package com.ramiyon.soulmath.domain.di

import com.ramiyon.soulmath.domain.usecase.login.LoginUseCase
import com.ramiyon.soulmath.domain.usecase.login.LoginUseCaseImpl
import com.ramiyon.soulmath.domain.usecase.register.RegisterUseCase
import com.ramiyon.soulmath.domain.usecase.register.RegisterUseCaseImpl
import org.koin.dsl.module


val useCaseModule = module {
    single<LoginUseCase> { LoginUseCaseImpl(get()) }
    single<RegisterUseCase> { RegisterUseCaseImpl(get()) }
}