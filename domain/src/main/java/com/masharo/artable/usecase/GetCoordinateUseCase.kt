package com.masharo.artable.usecase

import com.masharo.artable.repository.CoordinateRepository
import kotlinx.coroutines.flow.Flow

class GetCoordinateUseCase(
    private val coordinateRepository: CoordinateRepository
) {

    suspend fun execute(): Flow<Result> {
        return coordinateRepository.getCoordinateStream()
    }

    data class Result(
        val position: Long
    )

}