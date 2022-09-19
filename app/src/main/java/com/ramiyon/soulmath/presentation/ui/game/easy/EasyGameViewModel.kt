package com.ramiyon.soulmath.presentation.ui.game.easy

import androidx.lifecycle.ViewModel
import com.ramiyon.soulmath.domain.usecase.game.GameUseCase

class EasyGameViewModel(
    private val useCase: GameUseCase
): ViewModel() {

    fun fetchQuestions(gameId: String) = useCase.fetchQuestions(gameId)

}