package com.masharo.artable.repository

import com.masharo.artable.database.dao.IPDao
import com.masharo.artable.database.entity.toGetIPUseCase
import com.masharo.artable.database.entity.toIPEntity
import com.masharo.artable.usecase.GetIPUseCase
import com.masharo.artable.usecase.SaveIPUseCase

class IPRepositoryDefault(
    private val ipDao: IPDao
) : IPRepository {

    override fun save(param: SaveIPUseCase.Param) {
        ipDao.update(param.toIPEntity())
    }

    override fun get(): GetIPUseCase.Result? {
        return ipDao.get().toGetIPUseCase()
    }

}