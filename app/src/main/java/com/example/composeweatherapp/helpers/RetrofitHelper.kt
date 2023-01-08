package com.example.composeweatherapp.helpers

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitHelper {
    const val baseUrl = "https://api.openweathermap.org/data/2.5/"
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val instance = Retrofit.Builder().baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create(moshi)).build()
}