package com.masharo.artable.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.masharo.artable.database.ARTableDatabase
import com.masharo.artable.service.CoordinateService
import com.masharo.artable.service.CoordinateServiceKtor
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.http.hostIsIp
import org.koin.dsl.module

val dataModule = module {

    single {
        Room.databaseBuilder(
            context = get(),
            klass = ARTableDatabase::class.java,
            name = ARTableDatabase.AR_TABLE_DATABASE_NAME
        ).build()
    }

    single {
        get<ARTableDatabase>().calibrationDao()
    }

    single {
        HttpClient(OkHttp) {
            install(WebSockets) {
                pingInterval = 10
            }
        }
    }

    single<CoordinateService> {
        CoordinateServiceKtor(
            client = get()
        )
    }

}