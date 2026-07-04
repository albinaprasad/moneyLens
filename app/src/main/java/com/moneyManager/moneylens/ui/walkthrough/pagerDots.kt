package com.moneyManager.moneylens.ui.walkthrough

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.moneyManager.moneylens.ui.theme.primary_09
import kotlin.math.floor

@Composable
fun WormPagerIndicator(
    pagerState: PagerState,
    pageCount: Int,
    modifier: Modifier = Modifier,
    dotSize: Dp = 8.dp,
    spacing: Dp = 16.dp,
    activeColor: Color = primary_09,
    inactiveColor: Color = Color.LightGray,

) {

    val density = LocalDensity.current

    val dotPx = with(density) { dotSize.toPx() }
    val spacingPx = with(density) { spacing.toPx() }

    val distance = dotPx + spacingPx

    Canvas(
        modifier = modifier
            .height(dotSize)
            .width((dotSize * pageCount) + (spacing * (pageCount - 1)))
    ) {

        val centerY = size.height / 2

        // Draw inactive dots
        repeat(pageCount) { index ->

            val centerX = index * distance + dotPx / 2

            drawCircle(
                color = inactiveColor,
                radius = dotPx / 2,
                center = Offset(centerX, centerY)
            )
        }

        //--------------------------------------------------------
        // Worm animation
        //--------------------------------------------------------

        val pageOffset =
            pagerState.currentPage + pagerState.currentPageOffsetFraction

        val current = floor(pageOffset).toInt()
        val fraction = pageOffset - current

        val head: Float
        val tail: Float

        if (fraction < 0.5f) {

            head =
                (current * distance) +
                        dotPx / 2 +
                        (fraction * 2f * distance)

            tail =
                (current * distance) +
                        dotPx / 2

        } else {

            head =
                ((current + 1) * distance) +
                        dotPx / 2

            tail =
                (current * distance) +
                        dotPx / 2 +
                        ((fraction - 0.5f) * 2f * distance)
        }

        drawRoundRect(
            color = activeColor,
            topLeft = Offset(tail, centerY - dotPx / 2),
            size = Size(head - tail, dotPx),
            cornerRadius = CornerRadius(dotPx / 2)
        )
    }
}