package com.ramiyon.soulmath.domain.usecase.onboard

import com.ramiyon.soulmath.domain.repository.SoulMathRepository

class OnBoardUseCaseImpl(
    private val repository: SoulMathRepository
): OnBoardUseCase {
    override fun getOnBoardTitle(page: Int) = repository.getOnBoardTitle(page)

    override fun getOnBoardSubtitle(page: Int) = repository.getOnBoardSubtitle(page)

    override fun savePrefHaveRunAppBefore(isFirstTime: Boolean) {
        TODO("Not yet implemented")
    }
}