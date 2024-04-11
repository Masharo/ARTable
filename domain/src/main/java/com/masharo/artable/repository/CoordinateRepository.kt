package com.masharo.artable.repository

import com.masharo.artable.usecase.GetCoordinateUseCase
import kotlinx.coroutines.flow.Flow

interface CoordinateRepository {

    suspend fun getCoordinateStream(): Flow<GetCoordinateUseCase.Result>

}