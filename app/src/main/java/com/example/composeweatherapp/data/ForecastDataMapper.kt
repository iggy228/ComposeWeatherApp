package com.example.composeweatherapp.data

import com.example.composeweatherapp.domain.ForecastData
import com.example.composeweatherapp.domain.ForecastDataListItem
import com.example.composeweatherapp.domain.ForecastDataListItemMain
import com.example.composeweatherapp.domain.ForecastDataListItemWeather

fun ForecastDataDto.toForecastData(): ForecastData {
    println(list)
    val forecastList: List<ForecastDataListItem> = list.map {
        ForecastDataListItem(
            weather = ForecastDataListItemWeather(
                main = it.weather[0].main,
                description = it.weather[0].description
            ),
            main = ForecastDataListItemMain(
                temperature = it.main.temp
            )
        )
    }

    return ForecastData(forecastList)
}