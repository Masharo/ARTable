package com.masharo.artable.presentation.app

import android.app.Application
import com.masharo.artable.di.appModule
import com.masharo.artable.di.dataModule
import com.masharo.artable.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ARTableApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ARTableApp)
            modules(listOf(appModule, domainModule, dataModule))
        }
    }

}