package com.masharo.artable.usecase

import com.masharo.artable.repository.IPRepository

class SaveIPUseCase(
    private val ipRepository: IPRepository
) {

    fun execute(param: Param) {
        ipRepository.save(param)
    }

    data class Param(
        val ip: String
    )

}