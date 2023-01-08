package com.example.composeweatherapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composeweatherapp.R
import com.example.composeweatherapp.data.WeatherApi
import com.example.composeweatherapp.data.WeatherRepository
import com.example.composeweatherapp.helpers.LocationServiceHelper
import com.example.composeweatherapp.helpers.RetrofitHelper
import com.example.composeweatherapp.viewmodel.WeatherViewModel
import com.example.composeweatherapp.viewmodel.WeatherViewModelFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

val datetime = Date()
val formatter = SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault())

fun roundTemperature(temperature: Double): Int = temperature.roundToInt()

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
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Box(modifier = Modifier.padding(padding)) {
            }
            Image(
                modifier = Modifier.fillMaxSize(),
                imageVector = ImageVector.vectorResource(R.drawable.home_background),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Column() {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            stringResource(R.string.app_name),
                            color = MaterialTheme.colorScheme.surface
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = {},
                        ) {
                            Icon(
                                modifier = Modifier.size(32.dp),
                                imageVector = Icons.Default.AddCircle,
                                tint = MaterialTheme.colorScheme.surface,
                                contentDescription = stringResource(R.string.add_new_city)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
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
                                roundTemperature(
                                    weatherUiState.weatherData!!.main.temperature
                                )
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
                                roundTemperature(
                                    weatherUiState.weatherData!!.main.minTemperature
                                )
                            }°C / ${
                                roundTemperature(
                                    weatherUiState.weatherData!!.main.maxTemperature
                                )
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
                    ) {
                        Text(text = weatherUiState.forecastData.toString())
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