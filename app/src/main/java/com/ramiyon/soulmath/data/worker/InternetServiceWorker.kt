package com.ramiyon.soulmath.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ramiyon.soulmath.data.source.local.LocalDataSource
import com.ramiyon.soulmath.data.source.remote.RemoteDataSource
import com.ramiyon.soulmath.data.util.LocalAnswer
import com.ramiyon.soulmath.data.util.RemoteResponse
import com.ramiyon.soulmath.util.toLeaderboardEntity
import com.ramiyon.soulmath.util.toStudentBody
import kotlinx.coroutines.flow.collect
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class InternetServiceWorker(
    ctx: Context,
    params: WorkerParameters
): CoroutineWorker(ctx, params), KoinComponent {

    private val remoteDataSource by inject<RemoteDataSource>()
    private val localDataSource by inject<LocalDataSource>()
    private val command = inputData.getString(workerCommand)

    override suspend fun doWork(): Result {
        return when(command) {
            WorkerCommand.WORKER_COMMAND_UPDATE_PROFILE.command -> updateStudentProfile()
            WorkerCommand.WORKER_COMMAND_UPDATE_XP.command -> updateStudentXp()
            WorkerCommand.WORKER_COMMAND_UPDATE_LEADERBOARD.command -> updateLeaderboard()
            else -> Result.retry()
        }
    }

    private suspend fun updateLeaderboard(): Result {
        var result = Result.retry()
        val studentId = inputData.keyValueMap[WorkerParams.STUDENT_ID.param] as String
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

    private suspend fun updateStudentProfile(): Result {
        var result = Result.retry()
        val studentId = inputData.keyValueMap[WorkerParams.STUDENT_ID.param] as String
        val flowStudentEntity = localDataSource.getStudentDetail(studentId)
        flowStudentEntity.collect {
            when(it) {
                is LocalAnswer.Success -> {
                    remoteDataSource.updateStudentProfile(
                        studentId, it.data.toStudentBody()
                    )
                    result = Result.success()
                }
                is LocalAnswer.Error -> Result.retry()
                is LocalAnswer.Empty -> Result.retry()
            }
        }
        return result
    }

    private suspend fun updateStudentXp(): Result {
        var result = Result.retry()
        val studentId = inputData.keyValueMap[WorkerParams.STUDENT_ID.param] as String
        localDataSource.getStudentDetail(studentId).collect {
            result = when(it) {
                is LocalAnswer.Success -> {
                    remoteDataSource.updateStudentXp(it.data.studentId, it.data.xp)
                    Result.success()
                }
                is LocalAnswer.Error -> Result.retry()
                is LocalAnswer.Empty -> Result.retry()
            }
        }
        return result
    }
}