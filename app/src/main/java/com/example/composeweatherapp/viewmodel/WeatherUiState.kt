package com.example.composeweatherapp.viewmodel

import com.example.composeweatherapp.domain.WeatherData

data class WeatherUiState(
    val weatherData: WeatherData?,
    val loading: Boolean = false,
    val error: String? = null
)