package com.moneyManager.moneylens.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class AppScreens {

    @Serializable
    data object Splash : AppScreens()

    @Serializable
    data object Walkthrough : AppScreens()
}
