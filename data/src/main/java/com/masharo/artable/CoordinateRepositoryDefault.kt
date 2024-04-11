package com.masharo.artable

import com.masharo.artable.model.toGetCoordinateUseCase
import com.masharo.artable.repository.CoordinateRepository
import com.masharo.artable.service.CoordinateService
import com.masharo.artable.usecase.GetCoordinateUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CoordinateRepositoryDefault(
    private val coordinateService: CoordinateService
) : CoordinateRepository {
    override suspend fun getCoordinateStream(): Flow<GetCoordinateUseCase.Result> {
        return coordinateService.getCoordinate().map { coordinate ->
            coordinate.toGetCoordinateUseCase()
        }
    }
}