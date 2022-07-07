package com.ramiyon.soulmath.base

import com.ramiyon.soulmath.data.source.remote.api.response.BaseResponse
import com.ramiyon.soulmath.data.util.RemoteResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class BaseRemoteResponse<RequestType> {

    private fun initialize() = flow {
        try {
            val response = call()
            val data = response.data
            if (response.status == "200") {
                if(data is List<*>) {
                    if (response.count == 0)
                        emit(RemoteResponse.Empty())
                    else
                        emit(RemoteResponse.Success(data))
                } else {
                    emit(RemoteResponse.Success(data))
                }
            } else {
                throw Exception(response.message)
            }
        } catch (e: Exception) {
            emit(RemoteResponse.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    abstract suspend fun call(): BaseResponse<RequestType>
    fun asFlow() = initialize()
}