package com.ramiyon.soulmath.domain.usecase.onboard

interface OnBoardUseCase {

    fun getOnBoardTitle(page: Int): String
    fun getOnBoardSubtitle(page: Int): String
    suspend fun savePrefHaveRunAppBefore(isFirstTime: Boolean)

}