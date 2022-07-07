package com.ramiyon.soulmath.base

import com.ramiyon.soulmath.data.util.LocalAnswer
import kotlinx.coroutines.flow.flow

abstract class BaseDatabaseAnswer<RequestType> {

    private val flowValue = flow {
        try {
            val value = callDatabase()
            if(value is List<*>) {
                if(value.isEmpty())
                    emit(LocalAnswer.Empty())
            }
            emit(LocalAnswer.Success(value))

        } catch (e: Exception) {
            emit(LocalAnswer.Error(e.toString()))
        }
    }

    private suspend fun singleValue(): LocalAnswer<RequestType> {
        try {
            val value = callDatabase()
            if(value is List<*>) {
                if(value.isEmpty()) {
                    return LocalAnswer.Empty()
                }
                return LocalAnswer.Success(value)
            }
            return LocalAnswer.Success(value)
        } catch (e: Exception) {
            return LocalAnswer.Error(e.toString())
        }
    }

    abstract suspend fun callDatabase(): RequestType

    fun doObservable() = flowValue
    suspend fun doSingleEvent() = singleValue()

}