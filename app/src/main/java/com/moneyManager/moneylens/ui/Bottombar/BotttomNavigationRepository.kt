package com.moneyManager.moneylens.ui.Bottombar

import com.moneyManager.moneylens.DataClass.BottomNavigationItems
import com.moneyManager.moneylens.R
import jakarta.inject.Inject

class BottomNavigationRepository @Inject constructor() {
    fun getBottomNavItems(): List<BottomNavigationItems>{
       return listOf(
            BottomNavigationItems("Home", R.drawable.home_ic),
            BottomNavigationItems("Charts", R.drawable.chart_ic),
            BottomNavigationItems("Wallet", R.drawable.wallet_ic),
            BottomNavigationItems("Profile", R.drawable.profile_circle_ic),
        )
    }
}