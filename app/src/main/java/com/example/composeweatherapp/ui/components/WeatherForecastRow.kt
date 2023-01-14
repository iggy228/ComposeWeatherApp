package com.example.composeweatherapp.ui.components

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.composeweatherapp.R
import com.example.composeweatherapp.domain.ForecastDataListItem
import com.example.composeweatherapp.utils.WeatherType
import com.example.composeweatherapp.utils.formatForecastDate
import com.example.composeweatherapp.utils.mapWeatherIcon
import java.util.*

@Composable
fun WeatherForecastRow(modifier: Modifier = Modifier, forecastData: List<ForecastDataListItem>) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .padding(bottom = 16.dp)
            .scrollable(
                rememberScrollState(),
                orientation = Orientation.Horizontal
            )
    ) {
        for (forecastItemIndex in forecastData.indices) {
            val nextDate = Calendar.getInstance()
            nextDate.add(Calendar.DATE, forecastItemIndex + 1)

            WeatherDailyForecastBox(
                temperature = forecastData[forecastItemIndex].main.temperature,
                icon = {
                    Icon(
                        painter = painterResource(
                            mapWeatherIcon[WeatherType.valueOf(
                                forecastData[forecastItemIndex].weather.main
                            )] ?: R.drawable.sunny
                        ),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.surface,
                    )
                },
                label = if (forecastItemIndex == 0) stringResource(R.string.tomorrow) else formatForecastDate(
                    forecastItemIndex + 1
                ),
            )
        }
    }
}