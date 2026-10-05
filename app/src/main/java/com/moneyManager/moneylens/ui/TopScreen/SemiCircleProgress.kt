package com.moneyManager.moneylens.ui.TopScreen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private fun formatCurrency(amount: Double): String {
    val formatter = java.text.NumberFormat.getNumberInstance(java.util.Locale.US)
    return if (amount < 0) {
        "-₹${formatter.format(-amount.toLong())}"
    } else {
        "₹${formatter.format(amount.toLong())}"
    }
}

@Composable
fun BudgetProgressCard(
    modifier: Modifier = Modifier,
    monthlySpent: Double = 0.0,
    monthlyLimit: Double = 0.0,
    annualSpent: Double = 0.0,
    annualLimit: Double = 0.0
) {
    var selectedTab by remember { mutableStateOf(0) }

    val currentSpent = if (selectedTab == 0) monthlySpent else annualSpent
    val currentLimit = if (selectedTab == 0) monthlyLimit else annualLimit

    val remainingAmount = maxOf(0.0, currentLimit - currentSpent)
    val progress = if (currentLimit > 0) {
        (currentSpent / currentLimit).toFloat().coerceIn(0f, 1f)
    } else {
        0f
    }

    val spentStr = formatCurrency(currentSpent)
    val limitStr = formatCurrency(currentLimit)
    val remainingStr = formatCurrency(remainingAmount)

    val accentColor = if (selectedTab == 0) {
        MaterialTheme.colorScheme.primary       // Monthly
    } else {
        MaterialTheme.colorScheme.tertiary      // Annual
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // -----------------------------
        // Monthly / Annual Toggle
        // -----------------------------
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(4.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            val tabs = listOf("Monthly", "Annual")
            tabs.forEachIndexed { index, title ->
                val isSelected = selectedTab == index
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            if (isSelected) accentColor
                            else Color.Transparent
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    TextButton(
                        onClick = { selectedTab = index },
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.heightIn(min = 24.dp)
                    ) {
                        Text(
                            title,
                            color = if (isSelected)
                                if (selectedTab == 0)
                                    MaterialTheme.colorScheme.onPrimary
                                else
                                    MaterialTheme.colorScheme.onTertiary
                            else
                                MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(32.dp))

        // -----------------------------
        // Progress + Remaining
        // -----------------------------
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .aspectRatio(1.8f),
            contentAlignment = Alignment.BottomCenter
        ) {
            val trackColor = MaterialTheme.colorScheme.surfaceVariant
            val progressColor = accentColor
            
            Canvas(modifier = Modifier.fillMaxSize()) {
                val strokeWidth = 14.dp.toPx()
                val padding = strokeWidth / 2
                val arcDiameter = size.width - strokeWidth
                
                // Track
                drawArc(
                    color = trackColor,
                    startAngle = 180f,
                    sweepAngle = 180f,
                    useCenter = false,
                    topLeft = Offset(padding, padding),
                    size = Size(arcDiameter, arcDiameter),
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )

                // Progress
                drawArc(
                    color = progressColor,
                    startAngle = 180f,
                    sweepAngle = progress * 180f,
                    useCenter = false,
                    topLeft = Offset(padding, padding),
                    size = Size(arcDiameter, arcDiameter),
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text(
                    "REMAINING",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelMedium,
                    letterSpacing = 1.sp
                )
                Text(
                    remainingStr,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        // -----------------------------
        // Spent / Limit Labels
        // -----------------------------
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(accentColor)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        spentStr,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    "Spent",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(start = 18.dp)
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        limitStr,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(accentColor)
                    )
                }
                Text(
                    "Limit",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(end = 18.dp)
                )
            }
        }
    }
}
