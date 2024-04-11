package com.masharo.artable.di

import com.masharo.artable.service.CoordinateService
import com.masharo.artable.service.CoordinateServiceKtor
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.http.hostIsIp
import org.koin.dsl.module

val dataModule = module {
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