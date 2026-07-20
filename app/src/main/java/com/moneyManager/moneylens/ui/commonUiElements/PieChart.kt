package com.moneyManager.moneylens.ui.commonUiElements

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
    slices: List<ChartSlice>,
    modifier: Modifier
) {
    val totalValue = slices.sumOf { it.value.toDouble() }.toFloat()
    var currentStartAngle = -90f

    Canvas(modifier = modifier.fillMaxSize(0.7f).aspectRatio(1f)) {
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
   CircularPieChart(
       modifier = Modifier.padding(50.dp),
       slices =emptyList()
   )
}