package com.masharo.artable.presentation.model

data class CalibrationUIState(
    val leftValue: Long,
    val rightValue: Long,
    val state: State = State.START
) {
    enum class State {
        START,
        CALIBRATION_LEFT,
        CALIBRATION_RIGHT
    }
}
