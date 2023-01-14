package com.example.composeweatherapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composeweatherapp.R
import com.example.composeweatherapp.data.WeatherApi
import com.example.composeweatherapp.data.WeatherRepository
import com.example.composeweatherapp.helpers.LocationServiceHelper
import com.example.composeweatherapp.helpers.RetrofitHelper
import com.example.composeweatherapp.ui.components.WeatherForecastRow
import com.example.composeweatherapp.ui.layouts.MainLayout
import com.example.composeweatherapp.utils.WeatherType
import com.example.composeweatherapp.utils.formatCurrentDate
import com.example.composeweatherapp.utils.mapWeatherIcon
import com.example.composeweatherapp.viewmodel.WeatherViewModel
import com.example.composeweatherapp.viewmodel.WeatherViewModelFactory
import java.util.*
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    weatherViewModel: WeatherViewModel = viewModel(
        factory = WeatherViewModelFactory(
            WeatherRepository(
                RetrofitHelper.instance.create(WeatherApi::class.java),
            ),
            LocationServiceHelper.locationService!!
        )
    )
) {
    val weatherUiState by weatherViewModel.weatherData.collectAsState()
    LaunchedEffect(key1 = null) {
        weatherViewModel.getActualWeatherDataAndForecast()
    }

    Scaffold(
        modifier = modifier,
    ) { padding ->
        if (weatherUiState.loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        if (weatherUiState.error != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = weatherUiState.error!!,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
        if (weatherUiState.weatherData != null && weatherUiState.forecastData != null) {
            MainLayout(modifier = Modifier.padding(padding)) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(
                            weatherUiState.weatherData!!.name,
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.surface
                        )
                        Text(
                            text = formatCurrentDate(),
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.surface
                        )
                        Text(
                            text = "${
                                weatherUiState.weatherData!!.main.temperature.roundToInt()
                            }°C",
                            style = MaterialTheme.typography.displayLarge,
                            color = MaterialTheme.colorScheme.surface,
                        )
                        Divider(
                            thickness = 2.dp,
                            color = MaterialTheme.colorScheme.surface,
                            modifier = Modifier.padding(start = 48.dp, top = 8.dp, end = 48.dp)
                        )
                        Icon(
                            modifier = Modifier.padding(top = 16.dp),

                            painter = painterResource(
                                mapWeatherIcon[WeatherType.valueOf(
                                    weatherUiState.weatherData!!.weather.main
                                )] ?: R.drawable.sunny
                            ),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.surface
                        )
                        Text(
                            text = weatherUiState.weatherData!!.weather.main,
                            color = MaterialTheme.colorScheme.surface,
                            style = MaterialTheme.typography.headlineSmall,
                        )
                        Text(
                            text = "${
                                weatherUiState.weatherData!!.main.minTemperature.roundToInt()
                            }°C / ${
                                weatherUiState.weatherData!!.main.maxTemperature.roundToInt()
                            }°C",
                            color = MaterialTheme.colorScheme.surface,
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    WeatherForecastRow(
                        forecastData = weatherUiState.forecastData!!.list.subList(
                            0,
                            3
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}