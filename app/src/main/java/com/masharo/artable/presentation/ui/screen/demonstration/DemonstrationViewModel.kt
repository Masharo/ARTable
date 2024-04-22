package com.masharo.artable.presentation.ui.screen.demonstration

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masharo.artable.presentation.model.DemonstrationUIState
import com.masharo.artable.usecase.CloseConnectCoordinateUseCase
import com.masharo.artable.usecase.GetCoordinateUseCase
import com.masharo.artable.usecase.GetSavedCoordinateUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DemonstrationViewModel(
    private val getCoordinateUseCase: GetCoordinateUseCase,
    private val closeConnectCoordinateUseCase: CloseConnectCoordinateUseCase,
    private val getSavedCoordinateUseCase: GetSavedCoordinateUseCase
) : ViewModel() {

    private var minScrollCoefficient = 1

    private val _uiState = MutableStateFlow(
        DemonstrationUIState()
    )

    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getSavedCoordinateUseCase.execute()?.let { coordinate ->
                if (coordinate.positionLeft != null && coordinate.positionRight != null) {
                    minScrollCoefficient = minOf(
                        coordinate.positionLeft!!,
                        coordinate.positionRight!!
                    ).toInt()
                    updateScrollCoefficient(
                        (
                            maxOf(coordinate.positionLeft!!, coordinate.positionRight!!) -
                            minScrollCoefficient
                        ).toInt()
                    )
                }
            }
        }
    }

    fun updateUri(value: Uri?) {
        _uiState.update { currentValue ->
            currentValue.copy(
                selectedImg = value
            )
        }
    }

    fun updateState(value: DemonstrationUIState.State) {
        when (value) {
            DemonstrationUIState.State.PLAY -> connect()
            DemonstrationUIState.State.PRE_PLAY -> close()
        }
        _uiState.update { currentValue ->
            currentValue.copy(
                state = value
            )
        }
    }

    private fun updateScrollCoefficient(value: Int) {
        _uiState.update { currentValue ->
            currentValue.copy(
                scrollCoefficient = value
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

    private fun close() {
        viewModelScope.launch(Dispatchers.IO) {
            closeConnectCoordinateUseCase.execute()
        }
    }

    private fun connect() {
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
                    updatePosition(it - minScrollCoefficient)
                }
        }
    }

}