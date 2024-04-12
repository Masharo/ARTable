package com.masharo.artable.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.masharo.artable.database.entity.CalibrationEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CalibrationDao {

    @Insert
    abstract suspend fun insert(param: CalibrationEntity)

    @Query("DELETE FROM calibration")
    abstract suspend fun clear()

    @Transaction
    open suspend fun update(param: CalibrationEntity) {
        clear()
        insert(param)
    }

    @Query("""
        SELECT *
        FROM calibration
        LIMIT 1
    """)
    abstract fun get(): Flow<List<CalibrationEntity>>

}