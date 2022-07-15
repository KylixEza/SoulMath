package com.ramiyon.soulmath.base

import android.content.Context
import androidx.work.*
import com.ramiyon.soulmath.data.util.LocalAnswer
import com.ramiyon.soulmath.data.util.RemoteResponse
import com.ramiyon.soulmath.data.worker.InternetServiceWorker
import com.ramiyon.soulmath.data.worker.WorkerCommand
import com.ramiyon.soulmath.data.worker.workerCommand
import com.ramiyon.soulmath.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

abstract class NetworkBoundWorker<Api, Database, Domain>(
    context: Context
) {

    private val result = flow {
        when(val apiResult = callApi().first()) {
            is RemoteResponse.Success -> {
                saveToDatabase(apiResult.data)
                val constraints: Constraints = Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()

                val data = Data.Builder()
                    .putString(workerCommand, callWorkerCommand().command)
                    .putAll(putParamsForWorkManager())
                    .build()

                val periodicWorkRequest: PeriodicWorkRequest =
                    PeriodicWorkRequestBuilder<InternetServiceWorker>(3, TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .setInputData(data)
                        .build()

                val workManager = WorkManager.getInstance(context)
                workManager.enqueueUniquePeriodicWork("Network Bound Worker", ExistingPeriodicWorkPolicy.KEEP, periodicWorkRequest)
                workManager.getWorkInfoByIdLiveData(periodicWorkRequest.id).observeForever {
                    if (it.state == WorkInfo.State.FAILED) {
                        workManager.enqueueUniquePeriodicWork("Network Bound Worker", ExistingPeriodicWorkPolicy.KEEP, periodicWorkRequest)
                    }
                }

                emit(Resource.Success(mapApiToDomain(apiResult.data)))
            }

            is RemoteResponse.Empty -> {
                emit(Resource.Empty())
            }

            is RemoteResponse.Error -> {
                var data: Database? = null
                CoroutineScope(Dispatchers.IO).launch {
                    loadFromDatabase().collect {
                        when(it) {
                            is LocalAnswer.Success -> {
                                data = it.data
                            }
                        }
                    }
                }.join()
                emit(Resource.Error(apiResult.errorMessage, mapDatabaseToDomain(data)))
            }
        }
    }

    abstract suspend fun callApi(): Flow<RemoteResponse<Api>>
    abstract fun loadFromDatabase(): Flow<LocalAnswer<Database>>
    abstract suspend fun putParamsForWorkManager(): MutableMap<String, *>
    abstract suspend fun saveToDatabase(data: Api)
    abstract fun callWorkerCommand(): WorkerCommand
    abstract fun mapApiToDomain(data: Api): Domain
    abstract fun mapDatabaseToDomain(data: Database?): Domain
    abstract suspend fun shouldRefresh(): Boolean

    fun doPeriodicWork() = result

}