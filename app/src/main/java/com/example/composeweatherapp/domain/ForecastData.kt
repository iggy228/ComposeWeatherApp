package com.example.composeweatherapp.domain

data class ForecastData(
    val list: List<ForecastDataListItem>
)

data class ForecastDataListItem(
    val main: ForecastDataListItemMain,
    val weather: ForecastDataListItemWeather,
)

data class ForecastDataListItemMain(
    val temperature: Double
)

data class ForecastDataListItemWeather(
    val main: String,
    val description: String
)