package com.masharo.artable.presentation.ui.screen.settings

import androidx.lifecycle.ViewModel
import com.masharo.artable.presentation.model.SettingsUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        SettingsUIState()
    )
    val uiState = _uiState.asStateFlow()

}