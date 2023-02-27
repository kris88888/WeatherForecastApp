package com.examples.weatherforecast.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DatabaseConstants.FAVORITE_TABLE_NAME)
data class Favorite(
    @PrimaryKey
    @ColumnInfo(name = DatabaseConstants.FAVORITE_COL_CITY)
    val city: String,
    @ColumnInfo(name = DatabaseConstants.FAVORITE_COL_COUNTRY)
    val country:String

)
