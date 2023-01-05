package com.example.composeweatherapp.domain

data class WeatherData(
    val name: String,
    val main: WeatherDataMain,
    val weather: WeatherDataWeather
)

data class WeatherDataMain(
    val temperature: Double,
    val minTemperature: Double,
    val maxTemperature: Double
)

data class WeatherDataWeather(
    val id: Int,
    val main: String,
    val description: String,
)