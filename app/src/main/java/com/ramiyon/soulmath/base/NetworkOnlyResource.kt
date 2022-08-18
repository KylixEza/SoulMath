package com.ramiyon.soulmath.base

import android.util.Log
import com.ramiyon.soulmath.data.util.RemoteResponse
import com.ramiyon.soulmath.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

abstract class NetworkOnlyResource<ResultType, RequestType> {

    private val result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = createCall().first()) {
            is RemoteResponse.Success<RequestType> -> {
                Log.d("Network only resource", apiResponse.data.toString())
                emit(Resource.Success(mapTransform(apiResponse.data)))
            }
            is RemoteResponse.Empty -> emit(Resource.Empty())
            is RemoteResponse.Error -> emit(Resource.Error<ResultType>(apiResponse.errorMessage))
        }
    }.flowOn(Dispatchers.IO)

    protected abstract suspend fun createCall(): Flow<RemoteResponse<RequestType>>

    protected abstract fun mapTransform(data: RequestType): ResultType

    fun asFlow(): Flow<Resource<ResultType>> = result
}