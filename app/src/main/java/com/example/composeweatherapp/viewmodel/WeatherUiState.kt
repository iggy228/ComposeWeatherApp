package com.example.composeweatherapp.viewmodel

import com.example.composeweatherapp.data.WeatherDataDto

data class WeatherUiState(
    val weatherData: WeatherDataDto?,
    val loading: Boolean = false,
    val error: String? = null
)