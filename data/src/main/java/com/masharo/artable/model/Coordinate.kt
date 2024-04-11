package com.masharo.artable.model

import com.masharo.artable.usecase.GetCoordinateUseCase
import kotlinx.serialization.Serializable

@Serializable
data class Coordinate(
    val position: Long
)

fun Coordinate.toGetCoordinateUseCase() = GetCoordinateUseCase.Result(
    position = position
)