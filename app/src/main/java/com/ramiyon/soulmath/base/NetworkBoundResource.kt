package com.ramiyon.soulmath.base

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.ramiyon.soulmath.data.connectivity.ConnectivityStatus
import com.ramiyon.soulmath.data.connectivity.NetworkConnectivityObserver
import com.ramiyon.soulmath.data.util.RemoteResponse
import com.ramiyon.soulmath.util.Resource
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<RequestType, ResultType>(
    private val context: Context
) {

    private val connectivityManager = NetworkConnectivityObserver(context)

    @RequiresApi(Build.VERSION_CODES.N)
    private val result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        if(shouldFetch().first()) {
            when(val response = createCall().first()) {
                is RemoteResponse.Success -> {
                    saveToDb(response.data)
                    emit(Resource.Success(mapTransform(response.data)))
                }
                is RemoteResponse.Empty -> {
                    emit(Resource.Empty())
                }
                is RemoteResponse.Error -> {
                    emit(Resource.Error(response.errorMessage))
                }
            }
        } else {
            emitAll(loadFromDb()?.map { Resource.Success(it) }!!)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun asFlow() = result


    abstract fun createCall(): Flow<RemoteResponse<RequestType>>
    protected abstract fun saveToDb(data: RequestType)
    protected abstract fun mapTransform(data: RequestType): ResultType

    open fun loadFromDb(): Flow<ResultType>? { return null }

    @RequiresApi(Build.VERSION_CODES.N)
    open suspend fun shouldFetch(): Flow<Boolean> = flow {
        var state = ConnectivityStatus.Unavailable
        connectivityManager.observe().collect {
            state = it
        }
        when(state) {
            ConnectivityStatus.Available -> emit(true)
            else -> emit(false)
        }
    }
}