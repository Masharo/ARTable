package com.masharo.artable.presentation.ui.screen.calibration

import androidx.lifecycle.ViewModel
import com.masharo.artable.presentation.model.CalibrationUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CalibrationViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        CalibrationUIState(
            leftValue = 0,
            rightValue = 0
        )
    )
    val uiState = _uiState.asStateFlow()

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