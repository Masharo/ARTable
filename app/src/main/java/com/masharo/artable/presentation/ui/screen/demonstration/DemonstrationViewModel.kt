package com.masharo.artable.presentation.ui.screen.demonstration

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masharo.artable.presentation.model.DemonstrationUIState
import com.masharo.artable.usecase.GetCoordinateUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DemonstrationViewModel(
    private val getCoordinateUseCase: GetCoordinateUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        DemonstrationUIState()
    )

    val uiState = _uiState.asStateFlow()

    init {
        loadCoordinates()
    }

    fun updateState(value: DemonstrationUIState.State) {
        _uiState.update { currentValue ->
            currentValue.copy(
                state = value
            )
        }
    }

    private fun updatePosition(value: Long) {
        _uiState.update { currentValue ->
            currentValue.copy(
                position = value
            )
        }
    }

    private fun loadCoordinates() {
        viewModelScope.launch(Dispatchers.IO) {
            getCoordinateUseCase.execute(
                param = GetCoordinateUseCase.Param(
                    type = GetCoordinateUseCase.Param.Type.NONE
                )
            )
                .map {
                    it.position
                }
                .collect {
                    updatePosition(it)
                    Log.wtf("NEW_POSITION", it.toString())
                }
        }
    }

}