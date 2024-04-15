package com.masharo.artable.usecase

import com.masharo.artable.repository.CoordinateRepository

class SaveCoordinateUseCase(
    private val coordinateRepository: CoordinateRepository
) {

    suspend fun execute(param: Param) {
        coordinateRepository.saveCoordinate(param)
    }

    data class Param(
        val positionLeft: Long?,
        val positionRight: Long?
    )

}