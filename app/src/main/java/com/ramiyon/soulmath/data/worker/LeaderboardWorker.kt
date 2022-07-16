package com.ramiyon.soulmath.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ramiyon.soulmath.data.source.local.LocalDataSource
import com.ramiyon.soulmath.data.source.remote.RemoteDataSource
import com.ramiyon.soulmath.data.util.LocalAnswer
import com.ramiyon.soulmath.data.util.RemoteResponse
import com.ramiyon.soulmath.util.toLeaderboardEntity
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LeaderboardWorker(
    ctx: Context,
    params: WorkerParameters
): CoroutineWorker(ctx, params), KoinComponent {

    private val remoteDataSource by inject<RemoteDataSource>()
    private val localDataSource by inject<LocalDataSource>()

    override suspend fun doWork(): Result {
        var result = Result.retry()
        remoteDataSource.fetchLeaderboard().collect {
            when(it) {
                is RemoteResponse.Success -> {
                    it.data?.map { leaderboard ->
                        result = when(localDataSource.insertAllLeaderboard(leaderboard.toLeaderboardEntity())) {
                            is LocalAnswer.Success -> Result.success()
                            is LocalAnswer.Empty -> Result.retry()
                            is LocalAnswer.Error -> Result.failure()
                        }
                    }
                }
                is RemoteResponse.Empty -> result = Result.retry()
                is RemoteResponse.Error -> result = Result.failure()
            }
        }
        return result
    }
}