package com.example.composeweatherapp.domain

data class ForecastData(
    val list: List<ForecastDataListItem>
)

class ForecastDataListItem(
    main: ForecastDataListItemMain,
    weather: ForecastDataListItemWeather,
)

class ForecastDataListItemMain(
    temperature: Double
)

class ForecastDataListItemWeather(
    main: String,
    description: String
)