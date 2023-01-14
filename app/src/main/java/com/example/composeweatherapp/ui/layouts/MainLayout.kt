package com.example.composeweatherapp.ui.layouts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import com.example.composeweatherapp.R
import com.example.composeweatherapp.ui.components.AppBar

@Composable
fun MainLayout(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(modifier = modifier) {
        Image(
            modifier = Modifier.fillMaxSize(),
            imageVector = ImageVector.vectorResource(R.drawable.home_background),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column {
            AppBar()
            content()
        }
    }
}