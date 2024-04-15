package com.masharo.artable.presentation.ui.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masharo.artable.presentation.model.SettingsUIState
import com.masharo.artable.usecase.GetCoordinateUseCase
import com.masharo.artable.usecase.GetIPUseCase
import com.masharo.artable.usecase.GetSavedCoordinateUseCase
import com.masharo.artable.usecase.SaveCoordinateUseCase
import com.masharo.artable.usecase.SaveIPUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val getIPUseCase: GetIPUseCase,
    private val saveIPUseCase: SaveIPUseCase,
    private val getSavedCoordinateUseCase: GetSavedCoordinateUseCase,
    private val saveCoordinateUseCase: SaveCoordinateUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        SettingsUIState()
    )
    val uiState = _uiState.asStateFlow()

    init {
        getIP()
        getCalibrate()
    }

    fun updateIP(value: String) {
        _uiState.update { currentValue ->
            currentValue.copy(
                ip = value
            )
        }
    }

    private fun getIP() {
        viewModelScope.launch(Dispatchers.IO) {
            updateIP(
                value = getIPUseCase.execute()?.ip ?: ""
            )
        }
    }

    private fun getCalibrate() {
        viewModelScope.launch(Dispatchers.IO) {
            val calibrate = getSavedCoordinateUseCase.execute()
            updateCalibration(
                leftValue = calibrate?.positionLeft?.toString() ?: "",
                rightValue = calibrate?.positionRight?.toString() ?: ""
            )
        }
    }

    fun saveIP(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            saveIPUseCase.execute(
                param = SaveIPUseCase.Param(
                    ip = value
                )
            )
        }
        updateIP(value)
    }

    fun saveCalibrate(
        leftValue: String,
        rightValue: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            saveCoordinateUseCase.execute(
                param = SaveCoordinateUseCase.Param(
                    positionLeft = leftValue.toLongOrNull(),
                    positionRight = rightValue.toLongOrNull()
                )
            )
        }
        updateCalibration(
            leftValue = leftValue,
            rightValue = rightValue
        )
    }

    fun updateCalibration(
        leftValue: String,
        rightValue: String
    ) {
        _uiState.update { currentValue ->
            currentValue.copy(
                leftPosition = leftValue.toLongOrNull(),
                rightPosition = rightValue.toLongOrNull()
            )
        }
    }

    fun updateIsChangeIP(value: Boolean) {
        _uiState.update { currentValue ->
            currentValue.copy(
                isChangeIP = value
            )
        }
    }

    fun updateIsChangeCalibration(value: Boolean) {
        _uiState.update { currentValue ->
            currentValue.copy(
                isChangeCalibration = value
            )
        }
    }

}