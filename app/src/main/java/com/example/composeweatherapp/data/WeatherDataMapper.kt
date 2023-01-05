package com.example.composeweatherapp.data

import com.example.composeweatherapp.domain.WeatherData
import com.example.composeweatherapp.domain.WeatherDataMain
import com.example.composeweatherapp.domain.WeatherDataWeather

fun WeatherDataDto.toWeatherData(): WeatherData {
    val dataMain = WeatherDataMain(
        temperature = main.temperature,
        minTemperature = main.minTemperature,
        maxTemperature = main.maxTemperature,
    )
    val weatherData = WeatherDataWeather(
        main = weather[0].main,
        id = weather[0].id,
        description = weather[0].description,
    )

    return WeatherData(
        name = name,
        main = dataMain,
        weather = weatherData
    )
}