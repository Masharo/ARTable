package com.masharo.artable.service

import com.masharo.artable.model.Coordinate
import com.masharo.artable.model.CoordinateResponse
import com.masharo.artable.model.Error
import com.masharo.artable.model.Success
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
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
import kotlinx.coroutines.flow.isActive
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.serialization.json.Json

class CoordinateServiceKtor(
    private val client: HttpClient
) : CoordinateService {

    private var session: WebSocketSession? = null

    override fun getCoordinate(ip: String): Flow<Coordinate> {
        return flow {
            session = client.webSocketSession {
                url("ws://${ip}:8080/chat")
            }
            val coordinates = session!!
                .incoming
                .consumeAsFlow()
                .filterIsInstance<Frame.Text>()
                .mapNotNull { Json.decodeFromString<Coordinate>(it.readText()) }
            emitAll(coordinates)
        }
    }

    override suspend fun close() {
        session?.close()
    }

}