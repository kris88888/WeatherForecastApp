package com.examples.weatherforecast.data.model

data class City(
    val coord: Coord,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val timezone: Int
)

data class Coord(
    val lat: Double,
    val lon: Double
)