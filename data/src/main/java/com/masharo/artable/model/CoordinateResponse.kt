package com.masharo.artable.model

import kotlinx.coroutines.flow.Flow

sealed interface CoordinateResponse

data object ErrorCoordinateResponse : CoordinateResponse
data class SuccessCoordinateResponse(
    val coordinate: Flow<Coordinate>
) : CoordinateResponse