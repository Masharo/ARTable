package com.masharo.artable.presentation.model

import com.masharo.artable.usecase.SaveIPUseCase

data class SettingsUIState(
    val ip: String = "",
    val leftPosition: Long? = null,
    val rightPosition: Long? = null,
    val isChangeIP: Boolean = false
)