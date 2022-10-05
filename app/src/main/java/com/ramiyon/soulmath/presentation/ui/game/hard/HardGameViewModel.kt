package com.ramiyon.soulmath.presentation.ui.game.hard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ramiyon.soulmath.domain.model.Question
import com.ramiyon.soulmath.domain.usecase.game.GameUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HardGameViewModel(
    private val useCase: GameUseCase
): ViewModel() {

    private val _question = MutableStateFlow<Question?>(null)
    val question = _question.asStateFlow()

    fun fetchQuestions(gameId: String) = useCase.fetchQuestions(gameId).asLiveData()
}