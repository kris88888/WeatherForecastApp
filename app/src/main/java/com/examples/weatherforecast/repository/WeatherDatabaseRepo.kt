package com.examples.weatherforecast.repository

import androidx.room.Query
import com.examples.weatherforecast.data.database.DatabaseConstants
import com.examples.weatherforecast.data.database.Favorite
import com.examples.weatherforecast.data.database.UnitDao
import com.examples.weatherforecast.data.database.WeatherDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDatabaseRepo @Inject constructor(val dao: WeatherDao, val unitDao:UnitDao) {

    fun getFavorites(): Flow<List<Favorite>> = dao.getFavorites()
    suspend fun insertFavorites(favorite: Favorite) = dao.addFavorite(favorite)
    suspend fun updateFavorites(favorite: Favorite) = dao.updateFavorite(favorite)
    suspend fun deleteFavorite(favorite: Favorite) = dao.deleteFavorite(favorite)
    suspend fun deleteAllFavorites() = dao.deleteAllFavorites()

    // Units.
    suspend fun addUnit(unit:com.examples.weatherforecast.data.database.Unit) =
        unitDao.addUnit(unit)

    suspend fun deleteAllUnits() = unitDao.deleteAllUnits()

    suspend fun deleteUnit(unit:com.examples.weatherforecast.data.database.Unit) = unitDao.deleteUnit(unit)
    fun getUnit():Flow<List<com.examples.weatherforecast.data.database.Unit>> =
        unitDao.getUnit()
    suspend fun updateUnit(unit:com.examples.weatherforecast.data.database.Unit) =
        unitDao.updateUnit(unit)

}