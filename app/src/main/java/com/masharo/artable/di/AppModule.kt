package com.masharo.artable.di

import com.masharo.artable.presentation.ui.screen.calibration.CalibrationViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::CalibrationViewModel)
}