package com.ramiyon.soulmath.base

import com.ramiyon.soulmath.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class DatabaseBoundResource<RequestType, ResultType> {

    private val result: Flow<Resource<ResultType>> = flow {

        emit(Resource.Loading())
    }

    fun doWork() = result

}