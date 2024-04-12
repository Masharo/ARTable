package com.masharo.artable.service

import com.masharo.artable.model.Coordinate
import kotlinx.coroutines.flow.Flow

interface CoordinateService {

    fun getCoordinate(): Flow<Coordinate>

    suspend fun close()

}