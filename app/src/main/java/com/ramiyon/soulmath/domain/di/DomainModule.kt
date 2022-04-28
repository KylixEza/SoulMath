package com.ramiyon.soulmath.domain.di

import com.ramiyon.soulmath.data.repository.SoulMathRepositoryImpl
import com.ramiyon.soulmath.domain.repository.SoulMathRepository
import com.ramiyon.soulmath.domain.usecase.login.LoginUseCase
import com.ramiyon.soulmath.domain.usecase.login.LoginUseCaseImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<SoulMathRepository> {
        SoulMathRepositoryImpl(get())
    }
}

val useCaseModule = module {
    single<LoginUseCase> { LoginUseCaseImpl(get()) }
}