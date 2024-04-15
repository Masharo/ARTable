package com.masharo.artable.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.masharo.artable.database.entity.IPEntity

@Dao
abstract class IPDao {

    @Insert
    abstract fun insert(param: IPEntity)

    @Query("""
        SELECT *
        FROM ip
        LIMIT 1
    """)
    abstract fun get(): List<IPEntity>

    @Query("DELETE FROM ip")
    abstract fun clear()

    @Transaction
    open fun update(param: IPEntity) {
        clear()
        insert(param)
    }

}