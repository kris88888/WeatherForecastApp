package com.examples.weatherforecast

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

    suspend fun getWeatherData(): ApiResult<WeatherResponse, Boolean, Exception> {
        return cachedResponse ?: repository.getWeatherData(city)
    }
}