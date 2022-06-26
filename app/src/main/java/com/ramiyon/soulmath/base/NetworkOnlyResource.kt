package com.ramiyon.soulmath.base

import com.ramiyon.soulmath.data.util.RemoteResponse
import com.ramiyon.soulmath.util.Resource
import kotlinx.coroutines.flow.*

abstract class NetworkOnlyResource<ResultType, RequestType> {

    private val result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = createCall().first()) {
            is RemoteResponse.Success<RequestType> -> emitAll(mapTransform(apiResponse.data).map {
                    Resource.Success(it)
                })
            is RemoteResponse.Empty -> emit(Resource.Empty())
            is RemoteResponse.Error -> emit(Resource.Error<ResultType>(apiResponse.errorMessage))
        }
    }

    protected abstract suspend fun createCall(): Flow<RemoteResponse<RequestType>>

    protected abstract fun mapTransform(data: RequestType): Flow<ResultType>

    fun asFlow(): Flow<Resource<ResultType>> = result
}