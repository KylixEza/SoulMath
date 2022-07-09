package com.ramiyon.soulmath.presentation.ui.onboard.screens

import androidx.lifecycle.ViewModel
import com.ramiyon.soulmath.data.source.dummy.getOnBoardContentByPage

class OnBoardScreenViewModel: ViewModel() {

    fun getOnBoardContentTitleByPage(page: Int) = getOnBoardContentByPage(page).first
    fun getOnBoardContentSubtitleByPage(page: Int) = getOnBoardContentByPage(page).second

}