package com.example.composeweatherapp.ui.screens

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import com.example.composeweatherapp.ui.components.WeatherDailyForecastBox
import com.example.composeweatherapp.ui.layouts.MainLayout
import com.example.composeweatherapp.utils.WeatherType
import com.example.composeweatherapp.utils.mapWeatherIcon
import com.example.composeweatherapp.viewmodel.WeatherViewModel
import com.example.composeweatherapp.viewmodel.WeatherViewModelFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

val datetime = Date()
val formatter = SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault())

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
        if (weatherUiState.loading || weatherUiState.weatherData == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
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
                            text = formatter.format(datetime),
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
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp)
                            .padding(bottom = 16.dp)
                            .scrollable(
                                rememberScrollState(),
                                orientation = Orientation.Horizontal
                            )
                    ) {
                        if (weatherViewModel.fiveDayDailyForecast?.list == null) {
                            Text(text = "Forecast wasn't loaded")
                        } else {
                            for (forecastItem in weatherViewModel.fiveDayDailyForecast!!.list.subList(
                                0,
                                3
                            )) {
                                WeatherDailyForecastBox(
                                    temperature = forecastItem.main.temperature,
                                    icon = {
                                        Icon(
                                            painter = painterResource(
                                                mapWeatherIcon[WeatherType.valueOf(
                                                    forecastItem.weather.main
                                                )] ?: R.drawable.sunny
                                            ),
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.surface,
                                        )
                                    },
                                    label = "Tommorow",
                                )
                            }
                        }
                    }
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