package com.examples.weatherforecast.repository

import android.util.Log
import com.examples.weatherforecast.data.model.Weather
import com.examples.weatherforecast.data.model.WeatherResponse
import com.examples.weatherforecast.network.ApiResult
import com.examples.weatherforecast.network.WeatherApi
import retrofit2.Retrofit
import javax.inject.Inject


class WeatherRepo @Inject constructor(val api: WeatherApi) {

    suspend fun getWeatherData(city: String, units:String): ApiResult<WeatherResponse, Boolean, java.lang.Exception> {
        return try {
            val response = api.fetchData(city, units = units)
            Log.d("WeatherRepo", "getWeatherData: ${response.toString()}")
            ApiResult(data = response)
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
            ApiResult(exception = ex)
        }
    }
}