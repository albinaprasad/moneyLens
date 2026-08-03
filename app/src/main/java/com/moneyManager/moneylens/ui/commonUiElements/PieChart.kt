package com.moneyManager.moneylens.ui.commonUiElements

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moneyManager.moneylens.DataClass.ChartSlice
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CircularPieChart(
    slices: List<ChartSlice>,
    modifier: Modifier = Modifier
) {
    val totalValue = slices.sumOf { it.value.toDouble() }.toFloat()
    val strokeWidthPx = 80f

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize(0.7f)
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        val density = LocalDensity.current
        val chartSizePx = with(density) { minOf(maxWidth, maxHeight).toPx() }
        val radius = (chartSizePx - strokeWidthPx) / 2f

        Canvas(modifier = Modifier.fillMaxSize()) {
            var currentStartAngle = -90f

            slices.forEach { slice ->
                val sweepAngle = if (totalValue > 0) (slice.value / totalValue) * 360f else 0f

                drawArc(
                    color = slice.color,
                    startAngle = currentStartAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round)
                )

                currentStartAngle += sweepAngle
            }
        }

        var currentStartAngle = -90f

        slices.forEach { slice ->
            val sweepAngle = if (totalValue > 0) (slice.value / totalValue) * 360f else 0f

            val middleAngleDegrees = currentStartAngle + (sweepAngle / 2f)
            val middleAngleRadians = Math.toRadians(middleAngleDegrees.toDouble())

            val badgeXOffsetPx = (radius * cos(middleAngleRadians)).toFloat()
            val badgeYOffsetPx = (radius * sin(middleAngleRadians)).toFloat()

            val badgeXOffsetDp = with(density) { badgeXOffsetPx.toDp() }
            val badgeYOffsetDp = with(density) { badgeYOffsetPx.toDp() }

            // Floating Badge Container aligned with TopScreen style
            Box(
                modifier = Modifier
                    .offset(x = badgeXOffsetDp, y = badgeYOffsetDp)
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = slice.name,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Text(
                        text = "₹${slice.value.toInt()}",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            currentStartAngle += sweepAngle
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PieChartPreview() {
    CircularPieChart(
        slices = listOf(
            ChartSlice("Needs", 500f, Color(0xFF3787E6)),
            ChartSlice("Wants", 300f, Color(0xFF05D2FF)),
            ChartSlice("Savings", 200f, Color(0xFFADC6FF))
        ),
        modifier = Modifier.padding(64.dp)
    )
}
