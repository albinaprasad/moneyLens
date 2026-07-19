package com.moneyManager.moneylens.ui.commonUiElements

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moneyManager.moneylens.DataClass.ChartSlice
import com.moneyManager.moneylens.ui.TopScreen.TopBar
import java.nio.file.WatchEvent

@Composable
fun CircularPieChart(
    //slices: List<ChartSlice>,
    modifier: Modifier
) {
    val slices = listOf(
        ChartSlice("Needs", 50f, Color(0xFF0A1931)),
        ChartSlice("Wants", 30f, Color(0xFF15305B)),
        ChartSlice("Savings", 20f, Color(0xFF7F91B7))
    )

    val rule702010 = listOf(
        ChartSlice("Needs", 70f, Color(0xFF0A1931)),
        ChartSlice("Wants", 20f, Color(0xFF15305B)),
        ChartSlice("Savings", 10f, Color(0xFF7F91B7))
    )

// Example of user data with 4 categories instead of 3
    val customUserData = listOf(
        ChartSlice("Rent", 40f, Color(0xFF0A1931)),
        ChartSlice("Food", 25f, Color(0xFF15305B)),
        ChartSlice("Leisure", 20f, Color(0xFF5C6E91)),
        ChartSlice("Investments", 15f, Color(0xFF7F91B7))
    )
    val totalValue = slices.sumOf { it.value.toDouble() }.toFloat()
    var currentStartAngle = -90f

    Canvas(modifier = modifier.fillMaxSize().aspectRatio(1f)) {
        slices.forEach { slice ->
            val sweepAngle = (slice.value / totalValue) * 360f

            drawArc(
                color = slice.color,
                startAngle = currentStartAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = 100f, cap = StrokeCap.Butt)
            )

            currentStartAngle += sweepAngle
        }
    }
}
@Preview
@Composable
fun preview(){
   CircularPieChart(modifier= Modifier.padding(50.dp))
}