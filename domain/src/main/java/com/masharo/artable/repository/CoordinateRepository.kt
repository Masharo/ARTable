package com.masharo.artable.repository

import com.masharo.artable.usecase.GetCoordinateUseCase
import com.masharo.artable.usecase.GetSavedCoordinateUseCase
import com.masharo.artable.usecase.SaveCoordinateUseCase
import kotlinx.coroutines.flow.Flow

interface CoordinateRepository {

    fun getCoordinateStream(ip: String): GetCoordinateUseCase.Result

    suspend fun saveCoordinate(param: SaveCoordinateUseCase.Param)

    fun getSavedCoordinate(): GetSavedCoordinateUseCase.Result?

    suspend fun closeConnect()

}