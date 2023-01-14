package com.example.composeweatherapp.data

import com.example.composeweatherapp.BuildConfig
import com.example.composeweatherapp.domain.ForecastData
import com.example.composeweatherapp.domain.WeatherData

class WeatherRepository(private val api: WeatherApi) {
    private val apiKey = BuildConfig.WEATHER_API_KEY

    suspend fun getWeatherData(
        lat: Double,
        long: Double,
        units: String = "metric"
    ): Result<WeatherData> {
        return try {
            Result.success(
                api.getCurrentWeatherData(lat, long, apiKey, units).toWeatherData()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun getForecastData(
        lat: Double,
        long: Double,
        units: String = "metric"
    ): Result<ForecastData> {
        return try {
            Result.success(
                api.getForecastWeatherData(lat, long, apiKey, units).toForecastData()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}