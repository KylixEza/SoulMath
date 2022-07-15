package com.ramiyon.soulmath

import androidx.lifecycle.LifecycleObserver
import org.koin.dsl.module

val lifecycleModule = module {
    single { SoulMathLifecycleObserver(get()) }
}