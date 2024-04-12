package com.masharo.artable.usecase

import com.masharo.artable.repository.CoordinateRepository

class CloseConnectCoordinateUseCase(
    private val coordinateRepository: CoordinateRepository
) {

    suspend fun execute() {
        coordinateRepository.closeConnect()
    }

}