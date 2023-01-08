package com.example.composeweatherapp.data

import com.example.composeweatherapp.BuildConfig

class WeatherRepository(private val api: WeatherApi) {
    private val apiKey = BuildConfig.WEATHER_API_KEY

    suspend fun getWeatherData(
        lat: Double,
        long: Double,
        units: String = "metric"
    ): WeatherDataDto? {
        try {
            return api.getCurrentWeatherData(lat, long, apiKey, units)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    suspend fun getForecastData(
        lat: Double,
        long: Double,
        units: String = "metric"
    ): ForecastDataDto? {
        try {
            return api.getForecastWeatherData(lat, long, apiKey, units)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}