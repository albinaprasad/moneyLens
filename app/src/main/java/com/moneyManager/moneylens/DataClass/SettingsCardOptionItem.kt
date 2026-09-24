package com.moneyManager.moneylens.DataClass

import androidx.compose.ui.graphics.vector.ImageVector
import com.moneyManager.moneylens.navigation.AppScreens


data class SettingsCardOptionItem(
    val title: String,
    val icon: ImageVector,
    val route: AppScreens
)
