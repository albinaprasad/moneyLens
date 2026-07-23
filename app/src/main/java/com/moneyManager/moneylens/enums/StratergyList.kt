package com.moneyManager.moneylens.enums

enum class StrategyList(val strategy:String) {
    BALANCED("Balanced - 50/30/20"),
    AGGRESSIVE("Aggressive - 70/20/10"),
    CONSERVATIVE("Conservative - 30/40/30");

    companion object{
        fun getAvailableStrategies(): List<String> = entries.map { it.strategy }
    }
}