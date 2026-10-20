package com.moneyManager.moneylens.ui.commonUiElements

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import com.moneyManager.moneylens.DataClass.ChartSlice
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CircularPieChart(
    slices: List<ChartSlice>,
    modifier: Modifier = Modifier,
    showLabels: Boolean = true
) {
    val validSlices = slices.filter { it.value > 0f }
    val totalValue = validSlices.sumOf { it.value.toDouble() }.toFloat()
    val strokeWidthPx = 80f
    val emptyRingColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize(0.7f)
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        val density = LocalDensity.current
        val chartSizePx = with(density) { minOf(maxWidth, maxHeight).toPx() }
        val radius = (chartSizePx - strokeWidthPx) / 2f

        val sweepAngles = calculateSweepAngles(validSlices, minSweepAngleDegrees = 6f)

        Canvas(modifier = Modifier.fillMaxSize()) {
            if (totalValue <= 0f || validSlices.isEmpty()) {
                drawArc(
                    color = emptyRingColor,
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(width = strokeWidthPx)
                )
            } else {
                var currentStartAngle = -90f

                validSlices.forEachIndexed { index, slice ->
                    val sweepAngle = sweepAngles.getOrElse(index) { 0f }

                    drawArc(
                        color = slice.color,
                        startAngle = currentStartAngle,
                        sweepAngle = sweepAngle,
                        useCenter = false,
                        style = Stroke(
                            width = strokeWidthPx,
                            cap = if (validSlices.size == 1) StrokeCap.Round else StrokeCap.Butt
                        )
                    )

                    currentStartAngle += sweepAngle
                }
            }
        }

        if (totalValue <= 0f || validSlices.isEmpty()) {
            Text(
                text = "No Data",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium
            )
        } else if (showLabels) {
            var currentStartAngle = -90f

            validSlices.forEachIndexed { index, slice ->
                val sweepAngle = sweepAngles.getOrElse(index) { 0f }

                val middleAngleDegrees = currentStartAngle + (sweepAngle / 2f)
                val middleAngleRadians = Math.toRadians(middleAngleDegrees.toDouble())

                val badgeXOffsetPx = (radius * cos(middleAngleRadians)).toFloat()
                val badgeYOffsetPx = (radius * sin(middleAngleRadians)).toFloat()

                val badgeXOffsetDp = with(density) { badgeXOffsetPx.toDp() }
                val badgeYOffsetDp = with(density) { badgeYOffsetPx.toDp() }

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
}

private fun calculateSweepAngles(
    slices: List<ChartSlice>,
    minSweepAngleDegrees: Float = 6f
): List<Float> {
    if (slices.isEmpty()) return emptyList()
    if (slices.size == 1) return listOf(360f)

    val totalValue = slices.sumOf { it.value.toDouble() }.toFloat()
    if (totalValue <= 0f) return List(slices.size) { 360f / slices.size }

    val rawAngles = slices.map { (it.value / totalValue) * 360f }
    val smallIndices = rawAngles.indices.filter { rawAngles[it] < minSweepAngleDegrees }.toSet()

    if (smallIndices.isEmpty()) {
        return rawAngles
    }

    val totalSmallReserved = smallIndices.size * minSweepAngleDegrees
    if (totalSmallReserved >= 360f) {
        return List(slices.size) { 360f / slices.size }
    }

    val remainingAngleBudget = 360f - totalSmallReserved
    val largeSumValue = slices.indices
        .filter { it !in smallIndices }
        .sumOf { slices[it].value.toDouble() }
        .toFloat()

    return slices.indices.map { index ->
        if (index in smallIndices) {
            minSweepAngleDegrees
        } else {
            if (largeSumValue > 0f) {
                ((slices[index].value / largeSumValue) * remainingAngleBudget)
            } else {
                remainingAngleBudget / (slices.size - smallIndices.size)
            }
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
            ChartSlice("Savings", 200f, Color(0xFFADC6FF)),
            ChartSlice("Entertainment", 1f, Color(0xFFFF5252))
        ),
        modifier = Modifier.padding(64.dp)
    )
}
