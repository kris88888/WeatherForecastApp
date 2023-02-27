package com.examples.weatherforecast.data.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [Favorite::class, com.examples.weatherforecast.data.database.Unit::class],
    exportSchema = false,
    version = 3
)

abstract class WeatherRoomDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun unitDao(): UnitDao
}