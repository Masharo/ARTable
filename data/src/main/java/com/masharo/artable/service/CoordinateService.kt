package com.masharo.artable.service

import com.masharo.artable.model.CoordinateResponse
import kotlinx.coroutines.flow.Flow

interface CoordinateService {

    fun getCoordinate(ip: String): Flow<CoordinateResponse>

    suspend fun close()

}