package com.moneyManager.moneylens.navigation

import kotlinx.serialization.Serializable

sealed class AppScreens {
    @Serializable
    data object Splash: AppScreens()
    @Serializable
    data object Walkthrough: AppScreens()
    @Serializable
    data object TopScreen: AppScreens()
    @Serializable
    data object StrategyScreen: AppScreens()
    @Serializable
    data object Charts: AppScreens()
    @Serializable
    data object Wallet: AppScreens()
    @Serializable
    data object Profile: AppScreens()
    @Serializable
    data object SaveScreen: AppScreens()
    @Serializable object SettingsScreen: AppScreens()
    @Serializable object InviteRoute: AppScreens()
    @Serializable object RateRoute: AppScreens()
    @Serializable object FeedbackRoute: AppScreens()
    @Serializable object FaqRoute: AppScreens()
    @Serializable object AboutRoute: AppScreens()

}
