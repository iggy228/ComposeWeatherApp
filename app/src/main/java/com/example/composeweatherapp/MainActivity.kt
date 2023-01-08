package com.example.composeweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composeweatherapp.helpers.LocationServiceHelper
import com.example.composeweatherapp.ui.screens.AddNewCity
import com.example.composeweatherapp.ui.screens.HomeScreen
import com.example.composeweatherapp.ui.theme.ComposeWeatherAppTheme
import com.google.android.gms.location.LocationServices

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LocationServiceHelper.provide(
            LocationServices.getFusedLocationProviderClient(this),
            application
        )

        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                if (permissions[android.Manifest.permission.ACCESS_FINE_LOCATION] == true && permissions[android.Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
                }
            }

        requestPermissionLauncher.launch(
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        setContent {
            ComposeWeatherApp()
        }
    }
}

@Composable
fun ComposeWeatherApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    ComposeWeatherAppTheme {
        NavHost(navController = navController, startDestination = "home", modifier = modifier) {
            composable("home") { HomeScreen() }
            composable("add-new-city") { AddNewCity() }
        }
    }
}