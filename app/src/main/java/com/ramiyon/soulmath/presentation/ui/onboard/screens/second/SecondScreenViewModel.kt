package com.ramiyon.soulmath.presentation.ui.onboard.screens.second

import androidx.lifecycle.ViewModel
import com.ramiyon.soulmath.data.source.dummy.getOnBoardContentByPage

class SecondScreenViewModel: ViewModel() {

    val title = getOnBoardContentByPage(1).first
    val subtitle = getOnBoardContentByPage(1).second
}