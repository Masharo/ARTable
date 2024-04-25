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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DemonstrationViewModel(
    private val getCoordinateUseCase: GetCoordinateUseCase,
    private val closeConnectCoordinateUseCase: CloseConnectCoordinateUseCase,
    private val getSavedCoordinateUseCase: GetSavedCoordinateUseCase
) : ViewModel() {

    private var minScrollCoefficient = 1
    private var step = 4
    private var prevPosition = Int.MIN_VALUE

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
            DemonstrationUIState.State.PRE_PLAY -> closeConnect()
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

    private fun closeConnect() {
        viewModelScope.launch(Dispatchers.IO) {
            closeConnectCoordinateUseCase.execute()
        }
    }

    fun updateError(value: Boolean) {
        _uiState.update { currentValue ->
            currentValue.copy(
                hasError = value
            )
        }
    }

    fun connect() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getCoordinateUseCase.execute(
                param = GetCoordinateUseCase.Param(
                    type = GetCoordinateUseCase.Param.Type.NONE
                )
            )

            result.collect {
                when (it) {
                    is GetCoordinateUseCase.SuccessResult -> {
                        val newPosition = it.position - minScrollCoefficient
                        if (newPosition !in (prevPosition - step)..(prevPosition + step)) {
                            updatePosition(it.position - minScrollCoefficient)

                        }
                    }
                    is GetCoordinateUseCase.ErrorResult -> {
                        updateError(true)
                        closeConnect()
                    }
                }
            }
        }
    }

    override fun onCleared() {
        closeConnect()
        super.onCleared()
    }

}