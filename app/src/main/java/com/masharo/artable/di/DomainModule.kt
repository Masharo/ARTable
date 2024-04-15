package com.masharo.artable.di

import com.masharo.artable.database.dao.IPDao
import com.masharo.artable.repository.CoordinateRepositoryDefault
import com.masharo.artable.repository.CoordinateRepository
import com.masharo.artable.repository.IPRepository
import com.masharo.artable.repository.IPRepositoryDefault
import com.masharo.artable.usecase.*
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {

    single<CoordinateRepository> {
        CoordinateRepositoryDefault(
            coordinateService = get(),
            coordinateDao = get()
        )
    }

    single<IPRepository> {
        IPRepositoryDefault(
            ipDao = get<IPDao>()
        )
    }

    factoryOf(::GetCoordinateUseCase)

    factoryOf(::SaveCoordinateUseCase)

    factoryOf(::GetSavedCoordinateUseCase)

    factoryOf(::CloseConnectCoordinateUseCase)

    factoryOf(::GetIPUseCase)

    factoryOf(::SaveIPUseCase)

}