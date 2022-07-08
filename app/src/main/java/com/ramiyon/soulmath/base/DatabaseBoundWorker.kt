package com.ramiyon.soulmath.base

import android.content.Context
import androidx.work.*
import com.ramiyon.soulmath.data.util.LocalAnswer
import com.ramiyon.soulmath.data.util.RemoteResponse
import com.ramiyon.soulmath.data.worker.InternetServiceWorker
import com.ramiyon.soulmath.data.worker.WorkerCommand
import com.ramiyon.soulmath.data.worker.workerCommand
import com.ramiyon.soulmath.util.Resource
import com.ramiyon.soulmath.util.isNetworkConnected
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

abstract class DatabaseBoundWorker<FromApi>(
    context: Context
) {

    private val result: Flow<Resource<FromApi>> = flow {
        saveToDatabase()
        emit(Resource.Loading())
        if(isNetworkConnected()) {
            when(val remoteResponse = uploadToServer().first()) {
                is RemoteResponse.Success -> emit(Resource.Success(remoteResponse.data))
                is RemoteResponse.Error -> emit(Resource.Error(remoteResponse.errorMessage))
            }
        } else {
            val constraints: Constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val data = Data.Builder()
                .putString(workerCommand, callWorkerCommand().command)
                .putAll(putParamsForWorkManager())
                .build()

            val oneTimeWorkRequest: OneTimeWorkRequest =
                OneTimeWorkRequest.Builder(InternetServiceWorker::class.java)
                    .setConstraints(constraints)
                    .setInputData(data)
                    .build()
            val workManager = WorkManager.getInstance(context)
            workManager.enqueue(oneTimeWorkRequest)
            workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest.id).observeForever {
                if (it.state == WorkInfo.State.FAILED) {
                    workManager.enqueue(oneTimeWorkRequest)
                }
            }
        }
    }

    abstract fun putParamsForWorkManager(): MutableMap<String, *>
    abstract fun callWorkerCommand(): WorkerCommand
    fun doWork() = result

    abstract suspend fun uploadToServer(): Flow<RemoteResponse<FromApi>>
    abstract suspend fun saveToDatabase(): LocalAnswer<Unit>

}