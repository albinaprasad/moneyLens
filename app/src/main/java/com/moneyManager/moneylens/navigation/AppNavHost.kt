package com.moneyManager.moneylens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.moneyManager.moneylens.ui.splashScreen.SplashScreen
import com.moneyManager.moneylens.ui.walkthrough.WalkThrough

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppScreens.Splash
    ) {
        composable<AppScreens.Splash> {
            SplashScreen(onSplashFinished = {
                navController.navigate(AppScreens.Walkthrough)
            })
        }

        composable<AppScreens.Walkthrough> {
            WalkThrough()
        }
    }
}