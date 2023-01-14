package com.example.composeweatherapp.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Return simple date in format dddd, MMMM D, YYYY
 */
fun formatCurrentDate(): String {
    val currentDate: Calendar = Calendar.getInstance()
    val formatter = SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault())
    return formatter.format(currentDate.time)
}

/**
 * Return simple date in format D. M. YYYY
 * @param days how many days must add to date
 */
fun formatForecastDate(days: Int): String {
    val currentDate: Calendar = Calendar.getInstance()
    currentDate.add(Calendar.DATE, days)
    val formatter = SimpleDateFormat("d. M. yyyy", Locale.getDefault())
    return formatter.format(currentDate.time)
}