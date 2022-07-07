package com.ramiyon.soulmath.base

import kotlinx.coroutines.flow.flow

abstract class BaseDatabaseResponse<RequestType> {

    private suspend fun initialize() {
        try {
            callDatabase()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    abstract suspend fun callDatabase(): () -> Unit

    fun asFlow() = flow { emit(initialize()) }
    suspend fun asSuspend() = initialize()

}