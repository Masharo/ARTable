package com.masharo.artable.di

import com.masharo.artable.CoordinateRepositoryDefault
import com.masharo.artable.repository.CoordinateRepository
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

    factoryOf(::GetCoordinateUseCase)

    factoryOf(::SaveCoordinateUseCase)

    factoryOf(::GetSavedCoordinateUseCase)

}