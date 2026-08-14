package com.moneyManager.moneylens.AnimationManager

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

object AnimationManager {

    @Composable
    fun Modifier.customScalingAnimation(
        visible: Boolean,
        durationMillis: Int = 300
    ): Modifier {
        val scale by animateFloatAsState(
            targetValue = if (visible) 1f else 0f,
            animationSpec = keyframes {
                this.durationMillis = durationMillis
                0.00f at 0                                    // Hidden
                0.33f at (durationMillis * 0.25f).toInt()     // Step 1
                0.66f at (durationMillis * 0.50f).toInt()     // Step 2
                1.00f at (durationMillis * 0.75f).toInt()     // Step 3
            },
            label = "pixelScaleAnimation"
        )

        return this.graphicsLayer {
            scaleX = scale
            scaleY = scale
            alpha = scale
        }
    }
    @Composable
    fun Modifier.customFadingAnimation(
        visible: Boolean,
        durationMillis: Int = 300
    ): Modifier {
        val pixelAlpha by animateFloatAsState(
            targetValue = if (visible) 1f else 0f,
            animationSpec = keyframes {
                this.durationMillis = durationMillis
                0.0f at 0                                     // 0ms
                0.5f at (durationMillis * 0.25f).toInt()      // ~75ms
                1.0f at (durationMillis * 0.50f).toInt()      // ~150ms
            },
            label = "pixelAlphaAnimation"
        )
        return this.graphicsLayer {
            alpha = pixelAlpha
        }
    }

}