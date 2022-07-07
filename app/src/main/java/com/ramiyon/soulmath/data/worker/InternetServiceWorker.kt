package com.ramiyon.soulmath.data.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.ramiyon.soulmath.domain.repository.SoulMathRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class InternetServiceWorker(
    ctx: Context,
    params: WorkerParameters
): Worker(ctx, params), KoinComponent {

    private val repository by inject<SoulMathRepository>()

    abstract fun saveToDatabase(): () -> Unit

    override fun doWork(): Result {
        saveToDatabase()
        return Result.success()
    }
}