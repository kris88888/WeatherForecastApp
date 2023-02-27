package com.examples.weatherforecast.ui.screens.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.examples.weatherforecast.data.model.WeatherResponse
import com.examples.weatherforecast.network.ApiResult
import com.examples.weatherforecast.repository.WeatherRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepo) : ViewModel() {
    private val TAG = "MainViewModel"

    var city: String = "Burlington"
    var cachedResponse: ApiResult<WeatherResponse, Boolean, Exception>? = null

    suspend fun getWeatherData(units: String): ApiResult<WeatherResponse, Boolean, Exception> {
        Log.d(TAG, "UNIT = $units")
        return repository.getWeatherData(city, units)
    }
}