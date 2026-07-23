package com.moneyManager.moneylens.ui.StratergyScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneyManager.moneylens.DataClass.StrategyUiState
import com.moneyManager.moneylens.enums.AppLaunchState
import com.moneyManager.moneylens.preferences.UserPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StrategyScreenViewModal @Inject constructor(
    val repository: StrategyRepository,
    val userPreference: UserPreference
): ViewModel() {

    private val _uiState = MutableStateFlow(StrategyUiState())
    val uiState: StateFlow<StrategyUiState> = _uiState.asStateFlow()

    init {
        getStrategyData()
        loadStrategyData(_uiState.value.selectedStrategy)
    }

  private fun getStrategyData() {
        val strategies = repository.getAvailableStrategy()
        _uiState.update {
            it.copy(
                availableStrategies = strategies,
            )
        }
    }
    fun onStrategySelected(newStrategy: String) {
        if (newStrategy == _uiState.value.selectedStrategy) return
        _uiState.update { it.copy(selectedStrategy = newStrategy) }
        loadStrategyData(newStrategy)
    }
    private fun loadStrategyData(strategy: String) {
        viewModelScope.launch {
            val chartSlices = repository.getStrategySlices(strategy)
            _uiState.update {
                it.copy(slices = chartSlices)
            }
        }
    }

    suspend fun setStrategyScreenCompleted() {
        userPreference.setLaunchState(AppLaunchState.HOME)
    }
}