package com.ramiyon.soulmath.presentation.ui.material.onboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramiyon.soulmath.data.source.dummy.MaterialOnBoardContent
import com.ramiyon.soulmath.domain.model.material.MaterialLearningPurpose
import com.ramiyon.soulmath.domain.model.material.MaterialOnBoard
import com.ramiyon.soulmath.domain.usecase.material.onboard.MaterialOnBoardUseCase
import com.ramiyon.soulmath.util.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MaterialOnBoardViewModel(
    private val useCase: MaterialOnBoardUseCase
): ViewModel() {

    private val _content = MutableSharedFlow<MaterialOnBoard>()
    val content = _content.asSharedFlow()

    private val _learningPurpose = MutableSharedFlow<MaterialLearningPurpose>()
    val learningPurpose = _learningPurpose.asSharedFlow()

    private val _contentState = MutableSharedFlow<Resource<List<MaterialOnBoard>>>()
    val contentState = _contentState.asSharedFlow()

    private val _learningPurposeState = MutableSharedFlow<Resource<MaterialLearningPurpose>>()
    val learningPurposeState = _learningPurposeState.asSharedFlow()

    fun fetchMaterialOnBoardContentById(materialId: String, page: Int) {
        viewModelScope.launch {
            useCase.fetchMaterialOnBoardContentById(materialId).collect {
                when(it) {
                    is Resource.Success -> {
                        it.data?.filter { it.page == page }?.first()
                            ?.let { it1 -> _content.emit(it1) }
                        _contentState.emit(it)
                    }
                    else -> {
                        _contentState.emit(it)
                    }
                }
            }
        }
    }

    fun fetchMaterialLearningPurposeById(materialId: String) {
        viewModelScope.launch {
            useCase.fetchMaterialLearningPurposeById(materialId).collect {
                when(it) {
                    is Resource.Success -> {
                        _learningPurpose.emit(it.data!!)
                        _learningPurposeState.emit(it)
                    }
                    else -> {
                        _learningPurposeState.emit(it)
                    }
                }
            }
        }
    }

    fun getDummyMaterialOnBoardContent(materialId: String, page: Int) = MaterialOnBoardContent.getMaterialOnBoardContentById(materialId, page)

    fun getDummyMaterialLearningPurpose(materialId: String) = MaterialOnBoardContent.getMaterialLearningPurposeById(materialId)
}