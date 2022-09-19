package com.ramiyon.soulmath.presentation.ui.game.medium

import androidx.lifecycle.ViewModel
import com.ramiyon.soulmath.domain.usecase.game.GameUseCase

class MediumGameViewModel(
    private val useCase: GameUseCase
): ViewModel() {

    fun fetchQuestions(gameId: String) = useCase.fetchQuestions(gameId)
}