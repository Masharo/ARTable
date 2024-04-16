package com.masharo.artable.service

import com.masharo.artable.model.Coordinate
import com.masharo.artable.model.CoordinateResponse
import com.masharo.artable.model.Success
import kotlinx.coroutines.flow.Flow

interface CoordinateService {

    fun getCoordinate(): CoordinateResponse

    suspend fun close()

}