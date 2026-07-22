package com.moneyManager.moneylens.ui.StratergyScreen

import androidx.compose.ui.graphics.Color
import com.moneyManager.moneylens.DataClass.ChartSlice
import kotlinx.coroutines.flow.flow
import java.util.concurrent.Flow
import javax.inject.Inject

class StrategyRepository @Inject constructor() {
    fun getAvailableStrategy(): List<String>{
        return  listOf(
            "Balanced - 50/30/20",
            "Aggressive - 70/20/10",
            "Conservative - 30/40/30"
        )
    }

     fun getStrategySlices(strategyName: String): List<ChartSlice> {
         val slices = when {
             strategyName.contains("50/30/20") -> listOf(
                 ChartSlice(name = "Needs", value = 50f, color = Color(0xFFEF4444)),
                 ChartSlice(name = "Wants", value = 30f, color = Color(0xFF3B82F6)),
                 ChartSlice(name = "Savings", value = 20f, color = Color(0xFF10B981))
             )

             strategyName.contains("70/20/10") -> listOf(
                 ChartSlice(name = "Needs", value = 70f, color = Color(0xFFEF4444)),
                 ChartSlice(name = "Wants", value = 20f, color = Color(0xFF10B981)),
                 ChartSlice(name = "Savings", value = 10f, color = Color(0xFF8B5CF6))
             )

             strategyName.contains("30/40/30") -> listOf(
                 ChartSlice(name = "Needs", value = 30f, color = Color(0xFFEF4444)),
                 ChartSlice(name = "Wants", value = 40f, color = Color(0xFF10B981)),
                 ChartSlice(name = "Savings", value = 30f, color = Color(0xFF3B82F6))
             )

             else -> listOf(
                 ChartSlice(name = "Needs", value = 35f, color = Color(0xFFEF4444)),
                 ChartSlice(name = "Wants", value = 25f, color = Color(0xFF3B82F6)),
                 ChartSlice(name = "Savings", value = 20f, color = Color(0xFF10B981)),
             )

         }
         return slices
     }

}