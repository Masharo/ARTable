package com.masharo.artable.presentation.model

import com.masharo.artable.usecase.SaveCoordinateUseCase

data class CalibrationUIState(
    val leftValue: Long,
    val rightValue: Long,
    val position: Long = 0L,
    val state: State = State.START
) {
    enum class State {
        START,
        CALIBRATION_LEFT,
        CALIBRATION_RIGHT
    }
}

fun CalibrationUIState.toSaveCoordinateUseCase() = SaveCoordinateUseCase.Param(
    positionLeft = leftValue,
    positionRight = rightValue
)
