package com.masharo.artable.presentation.ui.screen.calibration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masharo.artable.presentation.model.CalibrationUIState
import com.masharo.artable.usecase.GetCoordinateUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CalibrationViewModel(
    private val getCoordinateUseCase: GetCoordinateUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        CalibrationUIState(
            leftValue = 0,
            rightValue = 0
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getCoordinateUseCase.execute()
                .map { it.position }
                .collect {
                    updatePosition(
                        position = it
                    )
                }
        }
    }

    private fun updatePosition(position: Long) {
        _uiState.update { currentValue ->
            currentValue.copy(
                position = position
            )
        }
    }

    fun navigateToCalculationState(
        state: CalibrationUIState.State
    ) {
        _uiState.update { currentValue ->
            currentValue.copy(
                state = state
            )
        }
    }

}