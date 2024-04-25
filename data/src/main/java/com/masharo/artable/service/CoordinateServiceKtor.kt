package com.masharo.artable.service

import android.util.Log
import com.masharo.artable.model.SuccessCoordinateResponse
import com.masharo.artable.model.CoordinateResponse
import com.masharo.artable.model.ErrorCoordinateResponse
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.url
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.serialization.json.Json

class CoordinateServiceKtor(
    private val client: HttpClient
) : CoordinateService {

    private var session: WebSocketSession? = null

    override fun getCoordinate(ip: String): Flow<CoordinateResponse> {
        return flow {
            try {
                session = client.webSocketSession {
                    url("ws://${ip}/ws")
                }
                val coordinates = session!!
                    .incoming
                    .consumeAsFlow()
                    .filterIsInstance<Frame.Text>()
                    .mapNotNull { Json.decodeFromString<SuccessCoordinateResponse>(it.readText()) }
                emitAll(coordinates)
            } catch (ex: Exception) {
                emit(ErrorCoordinateResponse)
            }
        }
    }

    override suspend fun close() {
        session?.close()
    }

}