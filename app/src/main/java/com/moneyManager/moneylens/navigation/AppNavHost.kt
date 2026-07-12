package com.moneyManager.moneylens.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.moneyManager.moneylens.enums.AppLaunchState
import com.moneyManager.moneylens.ui.TopScreen.TopScreen
import com.moneyManager.moneylens.ui.splashScreen.SplashScreen
import com.moneyManager.moneylens.ui.walkthrough.WalkThrough

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppScreens.Splash,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(400)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(400)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400)
            )
        }
    ) {

        composable<AppScreens.Splash> {
            SplashScreen(onSplashFinished = { state ->
                val destination = when (state) {
                    AppLaunchState.HOME -> AppScreens.TopScreen
                    else -> AppScreens.Walkthrough
                }
                
                navController.navigate(destination) {
                    popUpTo<AppScreens.Splash> {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            })
        }

        composable<AppScreens.Walkthrough> {
            WalkThrough {
                navController.navigate(AppScreens.TopScreen) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        }

        composable<AppScreens.TopScreen> {
            TopScreen()
        }
    }
}