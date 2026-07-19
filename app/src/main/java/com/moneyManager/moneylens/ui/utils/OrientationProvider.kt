package com.moneyManager.moneylens.ui.utils

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalConfiguration

enum class DeviceOrientation {
    PORTRAIT,
    LANDSCAPE
}

val LocalDeviceOrientation = staticCompositionLocalOf { DeviceOrientation.PORTRAIT }

@Composable
fun OrientationProvider(content: @Composable () -> Unit) {
    val configuration = LocalConfiguration.current
    val orientation = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        DeviceOrientation.LANDSCAPE
    } else {
        DeviceOrientation.PORTRAIT
    }

    CompositionLocalProvider(LocalDeviceOrientation provides orientation) {
        content()
    }
}

@Composable
fun isLandscape(): Boolean = LocalDeviceOrientation.current == DeviceOrientation.LANDSCAPE

@Composable
fun isPortrait(): Boolean = LocalDeviceOrientation.current == DeviceOrientation.PORTRAIT
