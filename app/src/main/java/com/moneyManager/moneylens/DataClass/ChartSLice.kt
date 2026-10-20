package com.moneyManager.moneylens.DataClass

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector


data class ChartSlice(
    val name: String,
    val value: Float,
    val color: Color,
    val icon: ImageVector? = null
)