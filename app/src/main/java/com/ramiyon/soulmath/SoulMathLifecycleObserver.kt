package com.ramiyon.soulmath

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.ramiyon.soulmath.domain.repository.SoulMathRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SoulMathLifecycleObserver(
    private val repository: SoulMathRepository
): LifecycleObserver {

    /**
     * > When the lifecycle event is ON_STOP, call the onStop() function
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.resetLeaderboard()
        }
    }

}