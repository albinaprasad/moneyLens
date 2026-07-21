package com.moneyManager.moneylens.ui.StratergyScreen

import androidx.lifecycle.ViewModel
import com.moneyManager.moneylens.DataClass.StrategyUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class StrategyScreenViewModal @Inject constructor(
    val repository: StrategyRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(StrategyUiState())
    val uiState: StateFlow<StrategyUiState> = _uiState.asStateFlow()

    init {
        getStrategyData()
    }

  private fun getStrategyData() {
        val strategies = repository.getAvailableStrategy()
        val defaultStrategy = strategies.firstOrNull().orEmpty()

        _uiState.update {
            it.copy(
                availableStrategies = strategies,
                selectedStrategy = defaultStrategy
            )
        }
    }

}