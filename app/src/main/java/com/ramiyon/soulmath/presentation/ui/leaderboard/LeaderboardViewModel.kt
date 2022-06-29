package com.ramiyon.soulmath.presentation.ui.leaderboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ramiyon.soulmath.domain.usecase.leaderboard.LeaderboardUseCase

class LeaderboardViewModel(
    private val useCase: LeaderboardUseCase
) : ViewModel() {

    fun fetchLeaderboard() = useCase.fetchLeaderboard().asLiveData()
    fun fetchStudentRank() = useCase.fetchStudentRank().asLiveData()

}