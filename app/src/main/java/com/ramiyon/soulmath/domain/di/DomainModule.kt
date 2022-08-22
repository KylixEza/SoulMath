package com.ramiyon.soulmath.domain.di

import com.ramiyon.soulmath.domain.usecase.home.HomeUseCase
import com.ramiyon.soulmath.domain.usecase.home.HomeUseCaseImpl
import com.ramiyon.soulmath.domain.usecase.leaderboard.LeaderboardUseCaseImpl
import com.ramiyon.soulmath.domain.usecase.leaderboard.LeaderboardUseCase
import com.ramiyon.soulmath.domain.usecase.login.LoginUseCase
import com.ramiyon.soulmath.domain.usecase.login.LoginUseCaseImpl
import com.ramiyon.soulmath.domain.usecase.material.dashboard.MaterialDashboardUseCase
import com.ramiyon.soulmath.domain.usecase.material.dashboard.MaterialDashboardUseCaseImpl
import com.ramiyon.soulmath.domain.usecase.material.onboard.MaterialOnBoardUseCase
import com.ramiyon.soulmath.domain.usecase.material.onboard.MaterialOnBoardUseCaseImpl
import com.ramiyon.soulmath.domain.usecase.onboard.OnBoardUseCase
import com.ramiyon.soulmath.domain.usecase.onboard.OnBoardUseCaseImpl
import com.ramiyon.soulmath.domain.usecase.profile.ProfileUseCase
import com.ramiyon.soulmath.domain.usecase.profile.ProfileUseCaseImpl
import com.ramiyon.soulmath.domain.usecase.register.RegisterUseCase
import com.ramiyon.soulmath.domain.usecase.register.RegisterUseCaseImpl
import com.ramiyon.soulmath.domain.usecase.splash.SplashUseCase
import com.ramiyon.soulmath.domain.usecase.splash.SplashUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {
    single<OnBoardUseCase> { OnBoardUseCaseImpl(get()) }
    single<SplashUseCase> { SplashUseCaseImpl(get()) }
    single<LoginUseCase> { LoginUseCaseImpl(get()) }
    single<RegisterUseCase> { RegisterUseCaseImpl(get()) }
    single<LeaderboardUseCase> { LeaderboardUseCaseImpl(get()) }
    single<ProfileUseCase> { ProfileUseCaseImpl(get()) }
    single<HomeUseCase> { HomeUseCaseImpl(get()) }
    single<MaterialOnBoardUseCase> { MaterialOnBoardUseCaseImpl() }
    single<MaterialDashboardUseCase> { MaterialDashboardUseCaseImpl(get()) }
}