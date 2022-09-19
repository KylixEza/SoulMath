package com.ramiyon.soulmath.domain.usecase.game

import com.ramiyon.soulmath.domain.model.Question
import com.ramiyon.soulmath.domain.repository.SoulMathRepository
import com.ramiyon.soulmath.util.Resource
import kotlinx.coroutines.flow.Flow

class GameUseCaseImpl(
    private val repository: SoulMathRepository
): GameUseCase {
    override fun fetchQuestions(gameId: String): Flow<Resource<List<Question>>> {
        return repository.fetchQuestions(gameId)
    }
}