package com.ramiyon.soulmath.util

import android.view.View
import com.ramiyon.soulmath.domain.model.Leaderboard

interface ResourceStateCallback<T> {

    fun onResourceLoading()
    fun onResourceSuccess(data: T)
    fun onResourceError(message: String?, data: List<Leaderboard>?)
    fun onResourceEmpty()

    val visible
        get() = View.VISIBLE
    val invisible
        get() = View.INVISIBLE
    val gone
        get() = View.GONE

}