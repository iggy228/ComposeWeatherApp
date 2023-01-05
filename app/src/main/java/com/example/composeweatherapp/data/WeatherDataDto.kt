package com.example.composeweatherapp.data

import com.squareup.moshi.Json

data class WeatherDataDto(
    val name: String,
    val main: WeatherDataDtoMain,
    val weather: List<WeatherDataDtoWeather>
)

data class WeatherDataDtoMain(
    @Json(name = "temp")
    val temperature: Double,
    @Json(name = "temp_min")
    val minTemperature: Double,
    @Json(name = "temp_max")
    val maxTemperature: Double
)

data class WeatherDataDtoWeather(
    val id: Int,
    val main: String,
    val description: String,
)