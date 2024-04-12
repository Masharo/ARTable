package com.masharo.artable.usecase

import com.masharo.artable.repository.CoordinateRepository
import kotlinx.coroutines.flow.Flow

class GetSavedCoordinateUseCase(
    private val coordinateRepository: CoordinateRepository
) {

    fun execute(): Flow<Result?> {
        return coordinateRepository.getSavedCoordinate()
    }

    data class Result(
        val positionLeft: Long,
        val positionRight: Long
    )

}