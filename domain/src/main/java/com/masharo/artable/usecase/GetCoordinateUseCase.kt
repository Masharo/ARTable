package com.masharo.artable.usecase

import com.masharo.artable.repository.CoordinateRepository
import com.masharo.artable.repository.IPRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter

class GetCoordinateUseCase(
    private val coordinateRepository: CoordinateRepository,
    private val ipRepository: IPRepository
) {

    fun execute(
        param: Param = Param()
    ): Flow<Result> {
        val coordinates = coordinateRepository.getSavedCoordinate() ?: GetSavedCoordinateUseCase.Result(
            positionLeft = 0,
            positionRight = 0
        )
        return when (param.type) {
            Param.Type.NONE -> coordinateRepository.getCoordinateStream(ip = ipRepository.get()?.ip ?: "")
            Param.Type.RANGE -> coordinateRepository.getCoordinateStream(ip = ipRepository.get()?.ip ?: "")
                .filter {
                    it.position in (coordinates.positionLeft ?: 0)..(coordinates.positionRight ?: 0)
                }
        }
    }

    data class Result(
        val position: Long
    )

    data class Param(
        val type: Type = Type.NONE
    ) {
        enum class Type {
            NONE,
            RANGE
        }
    }

}