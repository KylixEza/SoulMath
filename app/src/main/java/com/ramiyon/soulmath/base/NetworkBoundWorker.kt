package com.ramiyon.soulmath.base

import com.ramiyon.soulmath.data.util.LocalAnswer
import com.ramiyon.soulmath.data.util.RemoteResponse
import com.ramiyon.soulmath.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

abstract class NetworkBoundWorker<Api, Database, Domain>() {

    private val result = flow {
        if (shouldRefresh() || isFirstTime()) when(val apiResult = callApi().first()) {
            is RemoteResponse.Success -> {
                saveToDatabase(apiResult.data)
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
        } else {
            loadFromDatabase().collect {
                when(it) {
                    is LocalAnswer.Success -> {
                        emit(Resource.Success(mapDatabaseToDomain(it.data)))
                    }
                }
            }
        }
    }

    abstract suspend fun callApi(): Flow<RemoteResponse<Api>>
    abstract fun loadFromDatabase(): Flow<LocalAnswer<Database>>
    abstract suspend fun saveToDatabase(data: Api)
    abstract fun mapApiToDomain(data: Api): Domain
    abstract fun mapDatabaseToDomain(data: Database?): Domain
    abstract suspend fun shouldRefresh(): Boolean
    abstract suspend fun isFirstTime(): Boolean

    fun asFlow() = result

}