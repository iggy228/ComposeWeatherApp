package com.example.composeweatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeweatherapp.data.WeatherApi
import com.example.composeweatherapp.data.WeatherRepository
import com.example.composeweatherapp.helpers.RetrofitHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val weatherRepository: WeatherRepository = WeatherRepository(
        RetrofitHelper.getInstance().create(WeatherApi::class.java)
    )
) : ViewModel() {
    private val _weatherData = MutableStateFlow(WeatherUiState(null))
    val weatherData: StateFlow<WeatherUiState> = _weatherData.asStateFlow()


    fun getActualWeatherData() {
        _weatherData.value = weatherData.value.copy(
            loading = true
        )
        viewModelScope.launch {
            _weatherData.value = WeatherUiState(
                weatherRepository.getWeatherData(
                    49.501797,
                    19.514966
                ),
                loading = false,
            )
        }
    }
}