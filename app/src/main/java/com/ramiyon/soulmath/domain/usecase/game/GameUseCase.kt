package com.ramiyon.soulmath.domain.usecase.game

import com.ramiyon.soulmath.domain.model.Question
import com.ramiyon.soulmath.util.Resource
import kotlinx.coroutines.flow.Flow

interface GameUseCase {
    fun fetchQuestions(gameId: String): Flow<Resource<List<Question>>>
}