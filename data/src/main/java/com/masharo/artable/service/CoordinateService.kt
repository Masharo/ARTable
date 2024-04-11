package com.masharo.artable.service

import com.masharo.artable.model.Coordinate
import kotlinx.coroutines.flow.Flow

interface CoordinateService {
    suspend fun getCoordinate(): Flow<Coordinate>
}