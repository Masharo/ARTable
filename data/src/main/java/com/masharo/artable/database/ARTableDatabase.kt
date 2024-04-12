package com.masharo.artable.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.masharo.artable.database.dao.CalibrationDao
import com.masharo.artable.database.entity.CalibrationEntity

@Database(
    version = 1,
    exportSchema = false,
    entities = [
        CalibrationEntity::class
    ]
)
abstract class ARTableDatabase : RoomDatabase() {

    abstract fun calibrationDao(): CalibrationDao

    companion object {
        const val AR_TABLE_DATABASE_NAME = "AR_TABLE_DATABASE"
    }

}