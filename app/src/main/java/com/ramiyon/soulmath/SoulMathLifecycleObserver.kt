package com.ramiyon.soulmath

import androidx.lifecycle.*
import com.ramiyon.soulmath.domain.repository.SoulMathRepository
import com.ramiyon.soulmath.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SoulMathLifecycleObserver(
    private val repository: SoulMathRepository
): DefaultLifecycleObserver {

    /**
     * > When the lifecycle event is ON_STOP, call the onStop() function
     */
    
    override fun onStop(owner: LifecycleOwner) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.resetLeaderboard()
        }
    }
    
    override fun onStart(owner: LifecycleOwner) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.isTodayTaken().collect { todayTaken ->
                when(todayTaken) {
                    is Resource.Success -> {
                        if (!todayTaken.data!!) {
                            repository.getCurrentDailyXp().collect { currentDailyXp ->
                                when(currentDailyXp) {
                                    is Resource.Success -> {
                                        if(currentDailyXp.data == null) {
                                            repository.resetLeaderboard()
                                        }
                                    }
                                    else -> {}
                                }
                            }
                        }
                    }
                    else -> {}
                }
            }
        }
    }
}