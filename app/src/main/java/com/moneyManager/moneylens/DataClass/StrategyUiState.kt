package com.moneyManager.moneylens.DataClass

data class StrategyUiState(
    val slices: List<ChartSlice> = emptyList(),
    val availableStrategies: List<String> = emptyList(),
    val selectedStrategy: String = "",

)
