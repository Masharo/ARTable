package com.masharo.artable.usecase

import com.masharo.artable.repository.IPRepository

class GetIPUseCase(
    private val ipRepository: IPRepository
) {

    fun execute(): Result? {
        return ipRepository.get()
    }

    data class Result(
        val ip: String
    )

}