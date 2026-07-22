package com.moneyManager.moneylens.ui.walkthrough

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneyManager.moneylens.enums.AppLaunchState
import com.moneyManager.moneylens.preferences.UserPreference
import com.moneyManager.moneylens.ui.walkthrough.data.WalkThroughPageData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalkThroughViewmodel @Inject constructor(
    private val userPreference: UserPreference
): ViewModel() {
    fun setWalkThroughCompleted() {
        viewModelScope.launch {
            userPreference.setLaunchState(AppLaunchState.STRATEGY)
        }
    }

    private val _pages = MutableStateFlow(
        listOf(
            WalkThroughPageData(title = "Take Control of Your Finances", description = "Start tracking your expenses, saving money, and achieving your financial goals."),
            WalkThroughPageData(title = "Simplify Your Budgeting", description = "Say goodbye to complex spreadsheets and confusing calculations."),
            WalkThroughPageData(title = "Achieve Financial Freedom", description = "Get the tools and insights you need to make smart financial decisions, save money, and build a secure future.")
        )
    )
    val pages : StateFlow<List<WalkThroughPageData>> = _pages


}