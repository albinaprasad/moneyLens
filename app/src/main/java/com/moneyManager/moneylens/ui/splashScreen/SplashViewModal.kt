package com.moneyManager.moneylens.ui.splashScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneyManager.moneylens.enums.AppLaunchState
import com.moneyManager.moneylens.preferences.UserPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SplashViewModal @Inject constructor(
    private val userPreference: UserPreference
): ViewModel() {

    val launchState = userPreference.currentAppState.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AppLaunchState.SPLASH
    )
}