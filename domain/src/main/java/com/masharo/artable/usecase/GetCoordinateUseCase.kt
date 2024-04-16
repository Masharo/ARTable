package com.masharo.artable.usecase

import com.masharo.artable.repository.CoordinateRepository
import kotlinx.coroutines.flow.Flow

class GetCoordinateUseCase(
    private val coordinateRepository: CoordinateRepository
) {

    fun execute(): Flow<Result> {
        try {
            return coordinateRepository.getCoordinateStream()
        } catch (ex: Exception) {
            println("МОЯ ОШИБКА")
            throw Exception("ERROR")
        }
    }

    data class Result(
        val position: Long
    )

}