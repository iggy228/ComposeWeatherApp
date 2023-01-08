package com.example.composeweatherapp.helpers

import android.app.Application
import com.example.composeweatherapp.data.LocationService
import com.google.android.gms.location.FusedLocationProviderClient

object LocationServiceHelper {
    var locationService: LocationService? = null

    fun provide(client: FusedLocationProviderClient, application: Application) {
        locationService = LocationService(client, application)
    }
}