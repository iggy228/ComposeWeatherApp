package com.example.composeweatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.composeweatherapp.data.LocationService
import com.example.composeweatherapp.data.WeatherRepository
import com.example.composeweatherapp.data.toForecastData
import com.example.composeweatherapp.data.toWeatherData
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val weatherRepository: WeatherRepository,
    private val locationService: LocationService
) : ViewModel() {
    private val _weatherData =
        MutableStateFlow(WeatherUiState(weatherData = null, forecastData = null))
    val weatherData: StateFlow<WeatherUiState> = _weatherData.asStateFlow()

    fun getActualWeatherDataAndForecast() {
        _weatherData.value = weatherData.value.copy(
            loading = true
        )

        val currentLocation = locationService.getCurrentLocation()

        currentLocation.addOnSuccessListener { location ->
            viewModelScope.launch {
                val currentWeather = async {
                    weatherRepository.getWeatherData(
                        (location?.latitude ?: 0.0),
                        (location?.longitude ?: 0.0)
                    )?.toWeatherData()
                }
                val forecastData = async {
                    weatherRepository.getForecastData(
                        (location?.latitude ?: 0.0),
                        (location?.longitude ?: 0.0)
                    )?.toForecastData()
                }
                _weatherData.value = weatherData.value.copy(
                    weatherData = currentWeather.await(),
                    forecastData = forecastData.await(),
                    loading = false,
                )
            }

        }.addOnFailureListener {
            viewModelScope.launch {
                val currentWeather = async {
                    weatherRepository.getWeatherData(
                        0.0, 0.0
                    )?.toWeatherData()
                }
                val forecastData = async {
                    weatherRepository.getForecastData(
                        0.0, 0.0
                    )?.toForecastData()
                }
                _weatherData.value = weatherData.value.copy(
                    weatherData = currentWeather.await(),
                    forecastData = forecastData.await(),
                    loading = false,
                )
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class WeatherViewModelFactory(
    private val weatherRepository: WeatherRepository,
    private val locationService: LocationService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherViewModel(weatherRepository, locationService) as T
    }
}