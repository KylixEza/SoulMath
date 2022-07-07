package com.ramiyon.soulmath.base

import com.ramiyon.soulmath.data.util.LocalResult
import kotlinx.coroutines.flow.flow

abstract class BaseDatabaseAnswer<RequestType> {

    private val flowValue = flow {
        try {
            val value = callDatabase()
            if(value is List<*>) {
                if(value.isEmpty())
                    emit(LocalResult.Empty())
            }
            emit(LocalResult.Success(value))

        } catch (e: Exception) {
            emit(LocalResult.Error(e.toString()))
        }
    }

    private suspend fun singleValue(): LocalResult<RequestType> {
        try {
            val value = callDatabase()
            if(value is List<*>) {
                if(value.isEmpty()) {
                    return LocalResult.Empty()
                }
                return LocalResult.Success(value)
            }
            return LocalResult.Success(value)
        } catch (e: Exception) {
            return LocalResult.Error(e.toString())
        }
    }

    abstract suspend fun callDatabase(): RequestType

    fun doObservable() = flowValue
    suspend fun doSingleEvent() = singleValue()

}