package com.ramiyon.soulmath.domain.usecase.onboard

interface OnBoardUseCase {

    fun getOnBoardTitle(page: Int): String
    fun getOnBoardSubtitle(page: Int): String
    fun savePrefHaveRunAppBefore(isFirstTime: Boolean)

}