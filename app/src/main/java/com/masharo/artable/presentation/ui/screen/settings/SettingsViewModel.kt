package com.masharo.artable.presentation.ui.screen.settings

import androidx.lifecycle.ViewModel
import com.masharo.artable.presentation.model.SettingsUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SettingsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        SettingsUIState()
    )
    val uiState = _uiState.asStateFlow()

    fun updateIsChangeIP(value: Boolean) {
        _uiState.update { currentValue ->
            currentValue.copy(
                isChangeIP = value
            )
        }
    }
}