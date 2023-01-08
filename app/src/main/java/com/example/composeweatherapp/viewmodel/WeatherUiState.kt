package com.example.composeweatherapp.viewmodel

import com.example.composeweatherapp.domain.ForecastData
import com.example.composeweatherapp.domain.WeatherData

data class WeatherUiState(
    val weatherData: WeatherData?,
    val forecastData: ForecastData?,
    val loading: Boolean = false,
    val error: String? = null
)