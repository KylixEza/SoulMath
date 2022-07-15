package com.ramiyon.soulmath.presentation.ui.leaderboard

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ramiyon.soulmath.domain.model.Leaderboard
import com.ramiyon.soulmath.domain.usecase.leaderboard.LeaderboardUseCase
import com.ramiyon.soulmath.util.Resource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LeaderboardViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val useCase: LeaderboardUseCase
) : ViewModel() {

    fun fetchLeaderboard(shouldFetch: Boolean) = useCase.fetchLeaderboard(shouldFetch).asLiveData()

    fun saveStateLeaderboard(data: List<Leaderboard>?) {
        savedStateHandle["leaderboard"] = data
    }

    val leaderboard = savedStateHandle.getStateFlow<List<Leaderboard>?>("leaderboard", listOf()).asLiveData()

    fun fetchStudentRank() = useCase.fetchStudentRank().asLiveData()

}