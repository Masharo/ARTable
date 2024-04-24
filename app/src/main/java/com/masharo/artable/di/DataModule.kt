package com.masharo.artable.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.masharo.artable.database.ARTableDatabase
import com.masharo.artable.database.dao.IPDao
import com.masharo.artable.service.CoordinateService
import com.masharo.artable.service.CoordinateServiceKtor
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.http.HttpStatusCode
import io.ktor.http.hostIsIp
import io.ktor.serialization.WebsocketContentConverter
import io.ktor.util.reflect.TypeInfo
import io.ktor.utils.io.charsets.Charset
import io.ktor.websocket.Frame
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.dsl.module

val dataModule = module {

    single {
        Room.databaseBuilder(
            context = get(),
            klass = ARTableDatabase::class.java,
            name = ARTableDatabase.AR_TABLE_DATABASE_NAME
        )
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(Dispatchers.IO).launch {
                        val sqlQuery = get<Context>()
                            .assets
                            .open("IP.sql")
                            .bufferedReader()
                            .use { it.readText() }
                        db.execSQL(sqlQuery)
                    }
                }
            })
            .build()
    }

    single {
        get<ARTableDatabase>().calibrationDao()
    }

    single {
        HttpClient(OkHttp) {
            install(WebSockets) {
                pingInterval = 10
            }
            this.expectSuccess = true
            HttpResponseValidator {
                validateResponse { response ->
                    if (response.status != HttpStatusCode.OK) throw Exception("MY BIG EXCEPTION")
                }
            }
        }
    }

    single<CoordinateService> {
        CoordinateServiceKtor(
            client = get()
        )
    }

    single<IPDao> {
        get<ARTableDatabase>().ipDao()
    }

}