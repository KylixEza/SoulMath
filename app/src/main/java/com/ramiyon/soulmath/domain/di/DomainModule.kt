package com.ramiyon.soulmath.domain.di

import com.ramiyon.soulmath.data.repository.SoulMathRepositoryImpl
import com.ramiyon.soulmath.domain.repository.SoulMathRepository
import com.ramiyon.soulmath.domain.usecase.login.LoginUseCase
import com.ramiyon.soulmath.domain.usecase.login.LoginUseCaseImpl
import com.ramiyon.soulmath.domain.usecase.register.RegisterUseCase
import com.ramiyon.soulmath.domain.usecase.register.RegisterUseCaseImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<SoulMathRepository> {
        SoulMathRepositoryImpl(get(), get())
    }
}

val useCaseModule = module {
    single<LoginUseCase> { LoginUseCaseImpl(get()) }
    single<RegisterUseCase> { RegisterUseCaseImpl(get()) }
}