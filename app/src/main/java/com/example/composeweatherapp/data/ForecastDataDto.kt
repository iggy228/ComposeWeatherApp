package com.example.composeweatherapp.data

data class ForecastDataDto(
    val list: List<ForecastDataDtoListItem>
)

data class ForecastDataDtoListItem(
    val main: ForecastDataDtoListItemMain,
    val weather: List<ForecastDataDtoListItemMainWeather>,
)

data class ForecastDataDtoListItemMain(
    val temp: Double
)

data class ForecastDataDtoListItemMainWeather(
    val main: String,
    val description: String
)