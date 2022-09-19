package com.ramiyon.soulmath.presentation.ui.game.hard

import androidx.lifecycle.ViewModel
import com.ramiyon.soulmath.domain.usecase.game.GameUseCase

class HardGameViewModel(
    private val useCase: GameUseCase
): ViewModel() {

    fun fetchQuestions(gameId: String) = useCase.fetchQuestions(gameId)
}