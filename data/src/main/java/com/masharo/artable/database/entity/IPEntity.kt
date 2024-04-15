package com.masharo.artable.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.masharo.artable.usecase.GetIPUseCase
import com.masharo.artable.usecase.SaveIPUseCase

@Entity(tableName = "ip")
data class IPEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ip_id")
    val ipId: Long = 0,

    @ColumnInfo(name = "value")
    val value: String
)

fun SaveIPUseCase.Param.toIPEntity() = IPEntity(
    value = ip
)

fun IPEntity.toGetIPUseCase() = GetIPUseCase.Result(
    ip = value
)

fun List<IPEntity>.toGetIPUseCase() = firstOrNull()?.toGetIPUseCase()