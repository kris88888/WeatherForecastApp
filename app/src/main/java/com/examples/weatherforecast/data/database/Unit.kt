package com.examples.weatherforecast.data.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DatabaseConstants.SETTINGS_TABLE_NAME)
data class Unit(

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = DatabaseConstants.SETTINGS_COL_UNITS)
    val unit: String,

    @ColumnInfo(name = DatabaseConstants.SETTINGS_COL_UNITS_DESC)
    val desc: String
)