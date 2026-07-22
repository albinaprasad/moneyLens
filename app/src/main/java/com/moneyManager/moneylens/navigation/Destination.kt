package com.moneyManager.moneylens.navigation

import kotlinx.serialization.Serializable

sealed class AppScreens {
    @Serializable
    data object Splash

    @Serializable
    data object Walkthrough

    @Serializable
    data object TopScreen

    @Serializable
    data object StrategyScreen
}
