package com.masharo.artable.presentation.model

import android.net.Uri

data class DemonstrationUIState(
    val state: State = State.PRE_PLAY,
    val position: Long = 0,
    val scrollCoefficient: Int = 1,
    val selectedImg: Uri? = null
) {
    enum class State {
        PRE_PLAY,
        PLAY
    }
}
