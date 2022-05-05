package com.ramiyon.soulmath.data.mechanism

import com.ramiyon.soulmath.data.source.remote.RemoteResponse
import com.ramiyon.soulmath.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.first

abstract class NetworkBoundRequest<RequestType> {
    private val result: Flow<Resource<Unit>> = flow {
        preRequest()
        emit(Resource.Loading())
        when (val remoteResponse = createCall().first()) {
            is RemoteResponse.Success<RequestType> -> {
                saveCallResult(remoteResponse.data)
                emit(Resource.Success(null))
            }
            is RemoteResponse.Error -> {
                emit(
                    Resource.Error<Unit>(
                        remoteResponse.errorMessage
                    )
                )
            }
        }
    } as Flow<Resource<Unit>>

    protected open suspend fun preRequest(){}

    protected abstract suspend fun createCall(): Flow<RemoteResponse<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow() = result
}