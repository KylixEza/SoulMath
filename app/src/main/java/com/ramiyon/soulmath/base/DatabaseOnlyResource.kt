package com.ramiyon.soulmath.base

import com.ramiyon.soulmath.data.util.LocalAnswer
import com.ramiyon.soulmath.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

abstract class DatabaseOnlyResource<RequestType, ResultType> {

    private val flowResult = flow<Resource<ResultType>> {
        emit(Resource.Loading())
        when(val result = loadFromDb().first()) {
            is LocalAnswer.Success -> emit(Resource.Success(mapTransform(result.data)))
            is LocalAnswer.Error -> emit(Resource.Error(result.errorMessage))
            is LocalAnswer.Empty -> emit(Resource.Empty())
        }
    }

    abstract suspend fun loadFromDb(): Flow<LocalAnswer<RequestType>>
    abstract fun mapTransform(data: RequestType): ResultType

    fun asFlow() = flowResult

}