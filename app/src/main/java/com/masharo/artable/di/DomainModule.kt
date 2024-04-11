package com.masharo.artable.di

import com.masharo.artable.CoordinateRepositoryDefault
import com.masharo.artable.repository.CoordinateRepository
import com.masharo.artable.usecase.GetCoordinateUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    single<CoordinateRepository> {
        CoordinateRepositoryDefault(
            coordinateService = get()
        )
    }
    factoryOf(::GetCoordinateUseCase)
}