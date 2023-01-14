package com.example.composeweatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.composeweatherapp.data.LocationService
import com.example.composeweatherapp.data.WeatherRepository
import com.example.composeweatherapp.domain.ForecastData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel(
    private val weatherRepository: WeatherRepository,
    private val locationService: LocationService
) : ViewModel() {
    private val _weatherData =
        MutableStateFlow(WeatherUiState(weatherData = null, forecastData = null))
    val weatherData: StateFlow<WeatherUiState> = _weatherData.asStateFlow()

    val fiveDayDailyForecast: ForecastData?
        get() {
            if (weatherData.value.forecastData == null) {
                return null
            }
            val filteredList = weatherData.value.forecastData!!.list.filterIndexed { index, _ ->
                index % 7 == 0
            }
            return ForecastData(filteredList)
        }

    fun getActualWeatherDataAndForecast() {
        _weatherData.value = weatherData.value.copy(
            loading = true
        )

        val currentLocation = locationService.getCurrentLocation()

        currentLocation.addOnSuccessListener { location ->
            viewModelScope.launch {
                val currentWeather =
                    withContext(Dispatchers.Default) {
                        weatherRepository.getWeatherData(
                            (location?.latitude ?: 0.0),
                            (location?.longitude ?: 0.0)
                        )
                    }
                val forecastData =
                    withContext(Dispatchers.Default) {
                        weatherRepository.getForecastData(
                            (location?.latitude ?: 0.0),
                            (location?.longitude ?: 0.0)
                        )
                    }
                if (currentWeather.isSuccess && forecastData.isSuccess) {
                    _weatherData.value = weatherData.value.copy(
                        weatherData = currentWeather.getOrNull(),
                        forecastData = forecastData.getOrNull(),
                        loading = false,
                    )
                } else {
                    _weatherData.value = weatherData.value.copy(
                        loading = false,
                        error = currentWeather.exceptionOrNull()?.message
                            ?: forecastData.exceptionOrNull()?.message
                    )
                }
            }
        }.addOnFailureListener {
            viewModelScope.launch {
                val currentWeather =
                    withContext(Dispatchers.Default) {
                        weatherRepository.getWeatherData(
                            0.0, 0.0
                        )
                    }
                val forecastData =
                    withContext(Dispatchers.Default) {
                        weatherRepository.getForecastData(
                            0.0, 0.0
                        )
                    }
                if (currentWeather.isSuccess && forecastData.isSuccess) {
                    _weatherData.value = weatherData.value.copy(
                        weatherData = currentWeather.getOrNull(),
                        forecastData = forecastData.getOrNull(),
                        loading = false,
                    )
                } else {
                    _weatherData.value = weatherData.value.copy(
                        loading = false,
                        error = currentWeather.exceptionOrNull()?.message
                            ?: forecastData.exceptionOrNull()?.message
                    )
                }
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