package com.moneyManager.moneylens.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.moneyManager.moneylens.enums.AppLaunchState
import com.moneyManager.moneylens.ui.AnalysisAndHistoryScreen.HistoryDetailsScreen
import com.moneyManager.moneylens.ui.CategoryEditScreen.CategoryEditScreen
import com.moneyManager.moneylens.ui.HistoryScreen.HistoryScreen
import com.moneyManager.moneylens.ui.HistoryScreen.HistoryViewModal
import com.moneyManager.moneylens.ui.SaveScreen.SaveScreen
import com.moneyManager.moneylens.ui.Settings.SettingsScreen
import com.moneyManager.moneylens.ui.StratergyScreen.StrategyScreen
import com.moneyManager.moneylens.ui.TopScreen.TopScreen
import com.moneyManager.moneylens.ui.splashScreen.SplashScreen
import com.moneyManager.moneylens.ui.walkthrough.WalkThrough

@Composable
fun AppNavHost(navController: NavHostController) {
    SharedTransitionLayout {
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
                        AppLaunchState.WALKTHROUGH -> AppScreens.Walkthrough
                        AppLaunchState.STRATEGY -> AppScreens.StrategyScreen
                        AppLaunchState.HOME -> AppScreens.TopScreen
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
                    navController.navigate(AppScreens.StrategyScreen) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            }

            composable<AppScreens.TopScreen> {
                TopScreen(navController)
            }

            composable<AppScreens.SaveScreen> {
                SaveScreen(navController)
            }


            composable<AppScreens.StrategyScreen> {
                StrategyScreen {
                    navController.navigate(AppScreens.TopScreen) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            }

            composable<AppScreens.SettingsScreen> {
                SettingsScreen(onNavigate = { route ->
                    navController.navigate(route)
                }
                )
            }

            navigation<AppScreens.HistoryGraph>(
                startDestination = AppScreens.HistoryScreen
            ) {
                composable<AppScreens.HistoryScreen> { backStackEntry ->

                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry<AppScreens.HistoryGraph>()
                    }

                    val viewModal: HistoryViewModal = hiltViewModel(parentEntry)

                    HistoryScreen(
                        viewModal = viewModal,
                        onBackClick = {
                            navController.popBackStack()
                        },
                        onNavigateToHistoryDetails = { categoryId, type ->
                            navController.navigate(
                                AppScreens.HistoryDetailsScreen(
                                    categoryId = categoryId,
                                    type = type
                                )
                            )
                        },
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this
                    )
                }

                composable<AppScreens.HistoryDetailsScreen> { backStackEntry ->

                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry<AppScreens.HistoryGraph>()
                    }

                    val viewModal: HistoryViewModal = hiltViewModel(parentEntry)

                    val route = backStackEntry.toRoute<AppScreens.HistoryDetailsScreen>()

                    HistoryDetailsScreen(
                        viewModal = viewModal,
                        categoryId = route.categoryId,
                        categoryType = route.type,
                        onBackClick = {
                            navController.popBackStack()
                        },
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this
                    )
                }
            }

            composable<AppScreens.CategoryEditScreen> {
                CategoryEditScreen()
            }
        }
    }
}