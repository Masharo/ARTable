package com.masharo.artable.usecase

import com.masharo.artable.repository.CoordinateRepository
import com.masharo.artable.repository.IPRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

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
        val result = coordinateRepository.getCoordinateStream(ip = ipRepository.get()?.ip ?: "")
        return if (param.type == Param.Type.RANGE) {
            result.filter { responseResult ->
                when (responseResult) {
                    is SuccessResult -> responseResult.position in
                            (coordinates.positionLeft?: 0)..(coordinates.positionRight ?: 0)
                    is ErrorResult -> true
                }
            }
        } else result
    }

    sealed interface Result

    data object ErrorResult : Result
    data class SuccessResult(
        val position: Long
    ) : Result

    data class Param(
        val type: Type = Type.NONE
    ) {
        enum class Type {
            NONE,
            RANGE
        }
    }

}