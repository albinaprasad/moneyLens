package com.moneyManager.moneylens.ui.walkthrough

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.pager.PagerState
import kotlin.math.abs
import kotlin.math.min

@Composable
fun WormPagerIndicator(
    pagerState: PagerState,
    pageCount: Int,
    modifier: Modifier = Modifier,
    dotSize: Dp = 8.dp,
    spacing: Dp = 8.dp,
    activeColor: Color = MaterialTheme.colorScheme.primary,
    inactiveColor: Color = MaterialTheme.colorScheme.surfaceVariant,
) {
    val density = LocalDensity.current
    val dotPx = with(density) { dotSize.toPx() }
    val spacingPx = with(density) { spacing.toPx() }
    val r = dotPx / 2
    val distance = dotPx + spacingPx

    // Single continuous source of truth — no piecewise branching
    val targetPage = pagerState.currentPage + pagerState.currentPageOffsetFraction
    val targetX = targetPage * distance + r

    // Tip tracks the finger closely and settles cleanly (no bounce) —
    // it should feel like it's "leading", not overshooting.
    val tipX by animateFloatAsState(
        targetValue = targetX,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "tipX"
    )
    // Tail lags behind — critically damped so it catches up smoothly
    // instead of oscillating, but still slower than the tip so a
    // visible stretch forms and relaxes gradually.
    val tailX by animateFloatAsState(
        targetValue = targetX,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "tailX"
    )

    val canvasWidth = (dotSize * pageCount) + (spacing * (pageCount - 1))

    Canvas(
        modifier = modifier
            .height(dotSize)
            .width(canvasWidth)
    ) {
        val centerY = size.height / 2
        val maxX = size.width - r

        // Inactive dots
        repeat(pageCount) { index ->
            val cx = index * distance + r
            drawCircle(
                color = inactiveColor,
                radius = r,
                center = Offset(cx, centerY)
            )
        }

        // Clamp so the drop never overshoots past the first/last dot
        val backX = tailX.coerceIn(r, maxX)
        val tip = tipX.coerceIn(r, maxX)

        val stretch = abs(tip - backX)

        if (stretch < 0.5f) {
            // At rest: plain circle, no degenerate path
            drawCircle(color = activeColor, radius = r, center = Offset(backX, centerY))
        } else {
            val dir = if (tip >= backX) 1f else -1f

            // Taper the tip radius slightly as the stretch grows, like a
            // real liquid drop thinning out — capped so it never fully
            // pinches off. This is what makes the motion read as fluid
            // rather than a rigid capsule sliding around.
            val stretchRatio = min(stretch / (distance), 1f)
            val tipR = r * (1f - 0.35f * stretchRatio)

            val topTangent = Offset(backX, centerY - r)
            val bottomTangent = Offset(backX, centerY + r)
            val tipTop = Offset(tip, centerY - tipR)
            val tipBottom = Offset(tip, centerY + tipR)

            // Control points pulled toward the tail for a smoother,
            // less "pinched" neck than a single shared control point.
            val ctrl1 = backX + dir * r * 1.15f
            val ctrl2 = tip - dir * tipR * 0.6f

            val path = Path().apply {
                moveTo(topTangent.x, topTangent.y)
                cubicTo(
                    ctrl1, centerY - r,
                    ctrl2, centerY - tipR,
                    tipTop.x, tipTop.y
                )
                arcTo(
                    rect = Rect(center = Offset(tip, centerY), radius = tipR),
                    startAngleDegrees = -90f,
                    sweepAngleDegrees = if (dir > 0) 180f else -180f,
                    forceMoveTo = false
                )
                cubicTo(
                    ctrl2, centerY + tipR,
                    ctrl1, centerY + r,
                    bottomTangent.x, bottomTangent.y
                )
                arcTo(
                    rect = Rect(center = Offset(backX, centerY), radius = r),
                    startAngleDegrees = 90f,
                    sweepAngleDegrees = if (dir > 0) 180f else -180f,
                    forceMoveTo = false
                )
                close()
            }

            drawPath(path, color = activeColor)
        }
    }
}