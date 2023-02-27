package com.examples.weatherforecast.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.examples.weatherforecast.data.database.Unit


@Dao
interface UnitDao {
    @Query("SELECT * FROM ${DatabaseConstants.SETTINGS_TABLE_NAME}")
    fun getUnit(): Flow<List<com.examples.weatherforecast.data.database.Unit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUnit(unit: com.examples.weatherforecast.data.database.Unit)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnit(unit:com.examples.weatherforecast.data.database.Unit)

    @Delete()
    suspend fun deleteUnit(unit:com.examples.weatherforecast.data.database.Unit)

    @Query("DELETE FROM ${DatabaseConstants.SETTINGS_TABLE_NAME}")
    suspend fun deleteAllUnits()


}