package com.moneyManager.moneylens.ui.commonUiElements

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moneyManager.moneylens.DataClass.ChartSlice
import com.moneyManager.moneylens.ui.theme.boxColor
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CircularPieChart(
    slices: List<ChartSlice>,
    modifier: Modifier = Modifier
) {
    val totalValue = slices.sumOf { it.value.toDouble() }.toFloat()
    val strokeWidthPx = 100f


    BoxWithConstraints(
        modifier = modifier.fillMaxSize(0.7f).aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        val density = LocalDensity.current
        val chartSizePx = with(density) { minOf(maxWidth, maxHeight).toPx() }
        val radius = (chartSizePx - strokeWidthPx) / 2f

        Canvas(modifier = Modifier.fillMaxSize()) {
            var currentStartAngle = -90f

            slices.forEach { slice ->
                val sweepAngle = (slice.value / totalValue) * 360f

                drawArc(
                    color = slice.color,
                    startAngle = currentStartAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(width = strokeWidthPx, cap = StrokeCap.Butt)
                )

                currentStartAngle += sweepAngle
            }
        }

        var currentStartAngle = -90f

        slices.forEach { slice ->
            val sweepAngle = (slice.value / totalValue) * 360f

            val middleAngleDegrees = currentStartAngle + (sweepAngle / 2f)
            val middleAngleRadians = Math.toRadians(middleAngleDegrees.toDouble())

            val badgeXOffsetPx = (radius * cos(middleAngleRadians)).toFloat()
            val badgeYOffsetPx = (radius * sin(middleAngleRadians)).toFloat()

            val badgeXOffsetDp = with(density) { badgeXOffsetPx.toDp() }
            val badgeYOffsetDp = with(density) { badgeYOffsetPx.toDp() }

            // Floating Badge Container
            Box(
                modifier = Modifier
                    .offset(x = badgeXOffsetDp, y = badgeYOffsetDp)
                    .shadow(
                        elevation = 6.dp,
                        shape = RoundedCornerShape(8.dp),
                        ambientColor = Color.Black,
                        spotColor = Color.Black
                    )
                    .background(
                        color = boxColor.copy(alpha = 0.5f).compositeOver(Color.White),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = slice.name,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    Text(
                        text = "₦${slice.value.toInt()}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }

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