package com.example.composeweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composeweatherapp.ui.screens.AddNewCity
import com.example.composeweatherapp.ui.screens.HomeScreen
import com.example.composeweatherapp.ui.theme.ComposeWeatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


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