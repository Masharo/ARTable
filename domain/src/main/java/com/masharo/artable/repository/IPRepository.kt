package com.masharo.artable.repository

import com.masharo.artable.usecase.GetIPUseCase
import com.masharo.artable.usecase.SaveIPUseCase

interface IPRepository {

    fun save(param: SaveIPUseCase.Param)

    fun get(): GetIPUseCase.Result?

}