package com.moneyManager.moneylens.ui.Bottombar

import com.moneyManager.moneylens.DataClass.BottomNavigationItems
import com.moneyManager.moneylens.R
import com.moneyManager.moneylens.navigation.AppScreens
import jakarta.inject.Inject

class BottomNavigationRepository @Inject constructor() {
    fun getBottomNavItems(): List<BottomNavigationItems>{
       return listOf(
            BottomNavigationItems("Home", R.drawable.home_ic, AppScreens.TopScreen),
            BottomNavigationItems("Charts", R.drawable.chart_ic, AppScreens.Charts),
            BottomNavigationItems("Wallet", R.drawable.wallet_ic, AppScreens.Wallet),
            BottomNavigationItems("Settings", R.drawable.profile_circle_ic, AppScreens.SettingsScreen),
        )
    }
}