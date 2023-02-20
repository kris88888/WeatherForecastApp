package com.examples.weatherforecast.data.model

data class WeatherResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherInformation>,
    val message: Double
)