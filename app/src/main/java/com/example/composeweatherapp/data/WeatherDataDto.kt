package com.example.composeweatherapp.data

import com.squareup.moshi.Json

data class WeatherDataDto(
    val main: WeatherDataMainDto
)

data class WeatherDataMainDto(
    @Json(name = "temp")
    val temperature: Double,
    @Json(name = "temp_min")
    val minTemperature: Double,
    @Json(name = "temp_max")
    val maxTemperature: Double
)