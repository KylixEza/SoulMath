package com.ramiyon.soulmath.util

import com.ramiyon.soulmath.domain.model.Leaderboard

interface ResourceStateCallback<T> {

    fun onResourceLoading()
    fun onResourceSuccess(data: T)
    fun onResourceError(message: String?, data: List<Leaderboard>?)
    fun onResourceEmpty()
}