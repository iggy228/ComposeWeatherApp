package com.example.composeweatherapp.data

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    suspend fun getCurrentWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") key: String,
        @Query("units") units: String
    ): WeatherDataDto
}