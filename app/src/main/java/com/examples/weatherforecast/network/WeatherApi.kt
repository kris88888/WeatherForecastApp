package com.examples.weatherforecast.network

import com.examples.weatherforecast.data.model.WeatherResponse
import com.examples.weatherforecast.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

/**
 * https://api.openweathermap.org/data/2.5/forecast/daily?lat=43.3255&lon=-79.7990&cnt=3&appid=9553b7c6ed3168be95d5e4fe68dc016b&units=metric
 */
@Singleton
interface WeatherApi {

    @GET("data/2.5/forecast/daily")
    suspend fun fetchData(
        @Query("q") city: String,
        @Query("cnt") cnt: String = "14",
        @Query("appid") appId: String = Constants.API_KEY,
        @Query("units") units: String = "metric"
    ): WeatherResponse
}

