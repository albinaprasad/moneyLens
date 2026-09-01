package com.moneyManager.moneylens.DataClass

import com.moneyManager.moneylens.navigation.AppScreens

data class BottomNavigationItems(
    val name: String,
    val icon: Int,
    val route: AppScreens
)