package com.masharo.artable.presentation.model

data class SettingsUIState(
    val ip: String = "",
    val leftPosition: Long? = null,
    val rightPosition: Long? = null,
    val isChangeIP: Boolean = false,
    val isChangeCalibration: Boolean = false
)