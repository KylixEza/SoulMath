package com.ramiyon.soulmath.presentation.ui.onboard.screens.third

import androidx.lifecycle.ViewModel
import com.ramiyon.soulmath.data.source.dummy.getOnBoardContentByPage

class ThirdScreenViewModel: ViewModel() {
    val title = getOnBoardContentByPage(2).first
    val subtitle = getOnBoardContentByPage(2).second
}