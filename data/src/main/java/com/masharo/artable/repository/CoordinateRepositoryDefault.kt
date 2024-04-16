package com.masharo.artable.repository

import com.masharo.artable.database.dao.CalibrationDao
import com.masharo.artable.database.entity.toCalibrationEntity
import com.masharo.artable.database.entity.toGetSavedCoordinateUseCase
import com.masharo.artable.model.Error
import com.masharo.artable.model.Success
import com.masharo.artable.model.toGetCoordinateUseCase
import com.masharo.artable.service.CoordinateService
import com.masharo.artable.usecase.GetCoordinateUseCase
import com.masharo.artable.usecase.GetSavedCoordinateUseCase
import com.masharo.artable.usecase.SaveCoordinateUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CoordinateRepositoryDefault(
    private val coordinateService: CoordinateService,
    private val coordinateDao: CalibrationDao
) : CoordinateRepository {

    override fun getCoordinateStream(): Flow<GetCoordinateUseCase.Result> {
        val result = coordinateService.getCoordinate()
        return when (result) {
            is Success -> result.coordinate.map { coordinate ->
                coordinate.toGetCoordinateUseCase()
            }
            is Error -> throw Exception("Твоя ошибка")
        }
//        return coordinateService.getCoordinate().map { coordinate ->
//            coordinate.toGetCoordinateUseCase()
//        }
    }

    override suspend fun saveCoordinate(param: SaveCoordinateUseCase.Param) {
        coordinateDao.update(
            param = param.toCalibrationEntity()
        )
    }

    override fun getSavedCoordinate(): GetSavedCoordinateUseCase.Result? {
        return coordinateDao.get().firstOrNull()?.toGetSavedCoordinateUseCase()
    }

    override suspend fun closeConnect() {
        coordinateService.close()
    }

}