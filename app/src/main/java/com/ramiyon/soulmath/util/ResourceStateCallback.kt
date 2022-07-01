package com.ramiyon.soulmath.util

interface ResourceStateCallback<T> {

    fun onResourceLoading()
    fun onResourceSuccess(data: T)
    fun onResourceError(message: String?)
    fun onResourceEmpty()
}