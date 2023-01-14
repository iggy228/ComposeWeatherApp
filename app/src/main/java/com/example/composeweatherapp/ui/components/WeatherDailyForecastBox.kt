package com.example.composeweatherapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.composeweatherapp.R
import kotlin.math.roundToInt

@Composable
fun WeatherDailyForecastBox(
    modifier: Modifier = Modifier,
    temperature: Double,
    icon: @Composable () -> Unit,
    roundTemperature: Boolean = true,
    label: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        icon()
        Text(
            text = "${if (roundTemperature) temperature.roundToInt() else temperature}°C",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.surface,
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.surface,
        )
    }
}

@Preview
@Composable
fun PreviewWeatherDailyForecastBox(
) {
    WeatherDailyForecastBox(
        temperature = 0.0,
        icon = { Icon(painter = painterResource(R.drawable.sunny), contentDescription = null) },
        label = "Tommorow"
    )
}