package com.masharo.artable.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.masharo.artable.usecase.GetSavedCoordinateUseCase
import com.masharo.artable.usecase.SaveCoordinateUseCase

@Entity(tableName = "calibration")
data class CalibrationEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "calibration_id")
    val calibrationId: Long = 0,

    @ColumnInfo(name = "position_left")
    val positionLeft: Long?,

    @ColumnInfo(name = "position_right")
    val positionRight: Long?
)

fun SaveCoordinateUseCase.Param.toCalibrationEntity() = CalibrationEntity(
    positionLeft = positionLeft,
    positionRight = positionRight
)

fun CalibrationEntity.toGetSavedCoordinateUseCase() = GetSavedCoordinateUseCase.Result(
    positionLeft = positionLeft,
    positionRight = positionRight
)