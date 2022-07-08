package com.ramiyon.soulmath.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.ramiyon.soulmath.data.source.remote.RemoteDataSource
import com.ramiyon.soulmath.data.source.remote.api.response.student.StudentBody
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class InternetServiceWorker(
    ctx: Context,
    params: WorkerParameters
): CoroutineWorker(ctx, params), KoinComponent {

    private val remoteDataSource by inject<RemoteDataSource>()
    private val command = inputData.getString(workerCommand)

    override suspend fun doWork(): Result {
        when(command) {
            WorkerCommand.WORKER_COMMAND_UPDATE_PROFILE.command -> updateStudentProfile()
            WorkerCommand.WORKER_COMMAND_UPDATE_XP.command -> updateStudentXp()
        }
        return Result.success()
    }

    private suspend fun updateStudentProfile(): Result {
        val studentId = inputData.keyValueMap[WorkerParams.STUDENT_ID.param] as String
        val studentBody = inputData.keyValueMap[WorkerParams.STUDENT_BODY.param] as StudentBody
        return try {
            remoteDataSource.updateStudentProfile(studentId, studentBody)
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private suspend fun updateStudentXp(): Result {
        val studentId = inputData.keyValueMap[WorkerParams.STUDENT_ID.param] as String
        val studentBody = inputData.keyValueMap[WorkerParams.STUDENT_BODY.param] as StudentBody
        return try {
            remoteDataSource.updateStudentXp(studentId, studentBody)
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}