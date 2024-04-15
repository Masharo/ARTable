package com.masharo.artable.presentation.ui.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masharo.artable.presentation.model.SettingsUIState
import com.masharo.artable.usecase.GetIPUseCase
import com.masharo.artable.usecase.SaveIPUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val getIPUseCase: GetIPUseCase,
    private val saveIPUseCase: SaveIPUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        SettingsUIState()
    )
    val uiState = _uiState.asStateFlow()

    init {
        getIP()
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

    fun updateIsChangeIP(value: Boolean) {
        _uiState.update { currentValue ->
            currentValue.copy(
                isChangeIP = value
            )
        }
    }

}