package com.ramiyon.soulmath.data.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.ramiyon.soulmath.data.source.remote.RemoteDataSource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class InternetServiceWorker(
    ctx: Context,
    params: WorkerParameters
): Worker(ctx, params), KoinComponent {

    private val remoteDataSource by inject<RemoteDataSource>()

    override fun doWork(): Result {
        return Result.success()
    }
}