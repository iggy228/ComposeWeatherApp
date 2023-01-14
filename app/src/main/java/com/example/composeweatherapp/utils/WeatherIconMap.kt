package com.example.composeweatherapp.utils

import com.example.composeweatherapp.R

enum class WeatherType {
    Snow,
    Rain,
    Drizzle,
    Thunderstorm,
    Atmosphere,
    Clear,
    Clouds,
}

val mapWeatherIcon = mapOf(
    WeatherType.Thunderstorm to R.drawable.thunderstorm,
    WeatherType.Drizzle to R.drawable.rainy,
    WeatherType.Rain to R.drawable.rainy,
    WeatherType.Snow to R.drawable.cloudy_snowing,
    WeatherType.Atmosphere to R.drawable.foggy,
    WeatherType.Clear to R.drawable.sunny,
    WeatherType.Clouds to R.drawable.cloudy
)
