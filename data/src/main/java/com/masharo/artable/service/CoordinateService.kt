package com.masharo.artable.service

import com.masharo.artable.model.CoordinateResponse

interface CoordinateService {

    fun getCoordinate(ip: String): CoordinateResponse

    suspend fun close()

}