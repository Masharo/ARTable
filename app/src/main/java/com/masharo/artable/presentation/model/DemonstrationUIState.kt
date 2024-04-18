package com.masharo.artable.presentation.model

data class DemonstrationUIState(
    val state: State = State.PRE_PLAY,
    val position: Long = 0
) {
    enum class State {
        PRE_PLAY,
        PLAY
    }
}
