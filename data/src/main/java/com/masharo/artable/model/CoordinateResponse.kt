package com.masharo.artable.model

import kotlinx.serialization.Serializable

sealed interface CoordinateResponse

data object ErrorCoordinateResponse : CoordinateResponse

@Serializable
data class SuccessCoordinateResponse(
    val position: Long
) : CoordinateResponse