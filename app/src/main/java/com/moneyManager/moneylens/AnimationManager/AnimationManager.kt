package com.moneyManager.moneylens.AnimationManager

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.EaseInQuart
import androidx.compose.animation.core.EaseOutQuart
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
            animationSpec = tween(
                durationMillis = durationMillis,
                easing = if (visible) EaseOutQuart else EaseInQuart
            ),
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

    @Composable
    fun Modifier.tabBounceAnimation(
        selected: Boolean
    ): Modifier {
        val scale by animateFloatAsState(
            targetValue = if (selected) 1.08f else 1f,
            animationSpec = spring(
                dampingRatio = 0.35f,
                stiffness = 500f
            ),
            label = "tabBounce"
        )

        return graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
    }

    /**
     * Applies a right-to-left slide and fade-in entrance animation to the composable.
     *
     * @param visible Controls whether the element should animate into view.
     * @param index Item index for calculated staggered delays.
     * @param baseDelay Initial delay before the first item starts animating (ms).
     * @param stepDelay Delay added per item index (ms).
     * @param duration Duration of the animation (ms).
     */
    @Composable
    fun Modifier.animateSlideInFromRight(
        visible: Boolean,
        index: Int = 0,
        baseDelay: Int = 0,
        stepDelay: Int = 100,
        duration: Int = 450
    ): Modifier {
        val totalDelay = baseDelay + (index * stepDelay)

        val animationSpec: AnimationSpec<Float> = tween(
            durationMillis = duration,
            delayMillis = totalDelay,
            easing = FastOutSlowInEasing
        )
        // Progress goes from 0f (hidden/off-screen) to 1f (fully visible/in position)
        val progress by animateFloatAsState(
            targetValue = if (visible) 1f else 0f,
            animationSpec = animationSpec,
            label = "SlideInFromRightProgress"
        )
        return this.graphicsLayer {
            // Translation from right off-screen (size.width) to natural position (0)
            translationX = (1f - progress) * size.width
            alpha = progress
        }
    }

    /**
     * Helper hook that provides a triggers state for single-shot screen entrance animations.
     */
    @Composable
    fun rememberEntranceState(): Boolean {
        var isVisible by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            isVisible = true
        }
        return isVisible
    }

}