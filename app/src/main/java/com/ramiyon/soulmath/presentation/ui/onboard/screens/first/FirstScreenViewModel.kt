package com.ramiyon.soulmath.presentation.ui.onboard.screens.first

import androidx.lifecycle.ViewModel
import com.ramiyon.soulmath.data.source.dummy.getOnBoardContentByPage

class FirstScreenViewModel: ViewModel() {

    val title = getOnBoardContentByPage(0).first
    val subtitle = getOnBoardContentByPage(0).second

}