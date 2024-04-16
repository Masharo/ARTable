package com.masharo.artable.model

import kotlinx.coroutines.flow.Flow

sealed interface CoordinateResponse

data object Error : CoordinateResponse
data class Success(
    val coordinate: Flow<Coordinate>
) : CoordinateResponse