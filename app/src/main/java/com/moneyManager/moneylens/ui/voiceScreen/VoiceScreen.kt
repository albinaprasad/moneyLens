package com.moneyManager.moneylens.ui.voiceScreen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moneyManager.moneylens.ui.commonUiElements.CommonTopBar

/**
 * A Google Search-style voice listening screen built with native Jetpack Compose.
 *
 * `voiceLevel` is ready for a future microphone integration (0f..1f). The bars
 * animate on their own for now, so the screen looks alive without microphone input.
 */
@Composable
fun VoiceInputScreen(
    modifier: Modifier = Modifier,
    title: String = "Voice Search",
    listeningText: String = "Listening...",
    voiceLevel: Float = 0f,
    showAutoSearchChip: Boolean = true,
    onBack: () -> Unit = {},
    onMore: () -> Unit = {},
    onSpeakNowClick: () -> Unit = {},
    onModeSelected: () -> Unit = {}
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CommonTopBar(
            heading = title,
            showStartIcon = true,
            showEndIcon = false,
            onStartClick = onBack,
            onEndClick = onMore
        )

        BoxWithConstraints(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            // Position the elements proportionally, like Google Voice Search
            Text(
                text = listeningText,
                color = Color(0xFF202124),
                fontSize = 43.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = maxHeight * 0.18f)
            )

            ListeningBars(
                voiceLevel = voiceLevel.coerceIn(0f, 1f),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = maxHeight * 0.44f)
            )

            SpeakNowButton(
                text = "Speak now",
                onClick = onSpeakNowClick,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = maxHeight * 0.68f)
            )
        }
    }
}


@Composable
private fun SpeakNowButton(
    text: String = "Speak now",
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val shape = CircleShape

    val interactionSource = remember {
        MutableInteractionSource()
    }

    val isPressed by interactionSource.collectIsPressedAsState()

    // Soft glass background
    val glassBrush = Brush.linearGradient(
        colors = listOf(
            Color(0xFFFFFFFF),
            Color(0xFFF0F4FF),
            Color(0xFFF5F0FF),
            Color(0xFFEFF3FF)
        )
    )

    // Matching lavender-blue border
    val borderBrush = Brush.linearGradient(
        colors = listOf(
            Color(0xFFFFFFFF),
            Color(0xFFC8D5FF),
            Color(0xFFD9C9FF),
            Color(0xFFB9C9F5),
            Color(0xFFFFFFFF)
        )
    )

    Row(
        modifier = modifier

            // IDLE: Soft raised outer shadow
            .then(
                if (!isPressed) {
                    Modifier.dropShadow(
                        shape = shape,
                        shadow = Shadow(
                            radius = 14.dp,
                            spread = 1.dp,
                            color = Color(0xFFA8B8E8),
                            alpha = 0.38f,
                            offset = DpOffset(0.dp, 5.dp)
                        )
                    )
                } else {
                    Modifier
                }
            )

            .clip(shape)
            .background(glassBrush)

            // Premium gradient border
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFB9C9F5),
                        Color(0xFFD4C5FF)
                    )
                ),
                shape = CircleShape
            )

            // PRESSED: Inner blue-violet shadow
            .then(
                if (isPressed) {
                    Modifier.innerShadow(
                        shape = shape,
                        shadow = Shadow(
                            radius = 9.dp,
                            spread = 2.dp,
                            color = Color(0xFF899ACB),
                            alpha = 0.32f,
                            offset = DpOffset(2.dp, 3.dp)
                        )
                    )
                } else {
                    Modifier
                }
            )

            // Instant response, no ripple
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.Button,
                onClick = onClick
            )

            .padding(
                horizontal = 28.dp,
                vertical = 14.dp
            ),

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Icon(
            imageVector = Icons.Default.AutoAwesome,
            contentDescription = null,
            tint = Color(0xFF6558E8),
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = text,
            color = Color(0xFF515575),
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium
        )
    }
}





@Composable
private fun ListeningBars(
    voiceLevel: Float,
    modifier: Modifier = Modifier
) {
    val transition = rememberInfiniteTransition(label = "listening_bars")

    val h1 by transition.animateFloat(
        initialValue = 82f,
        targetValue = 126f,
        animationSpec = infiniteRepeatable(tween(680, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "bar_1"
    )
    val h2 by transition.animateFloat(
        initialValue = 120f,
        targetValue = 78f,
        animationSpec = infiniteRepeatable(tween(760, delayMillis = 110, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "bar_2"
    )
    val h3 by transition.animateFloat(
        initialValue = 88f,
        targetValue = 124f,
        animationSpec = infiniteRepeatable(tween(720, delayMillis = 210, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "bar_3"
    )
    val h4 by transition.animateFloat(
        initialValue = 126f,
        targetValue = 84f,
        animationSpec = infiniteRepeatable(tween(810, delayMillis = 70, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "bar_4"
    )

    val heights = listOf(h1, h2, h3, h4)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        heights.forEachIndexed { index, animatedHeight ->
            ListeningBar(
                height = (animatedHeight + voiceLevel * (if (index % 2 == 0) 25f else 18f)).dp
            )
        }
    }
}

@Composable
private fun ListeningBar(height: Dp) {
    Box(
        modifier = Modifier
            .width(48.dp)
            .height(height)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 2.dp, vertical = 3.dp)
                .clip(CircleShape)
                .background(Color(0xFF7EA4FF).copy(alpha = 0.20f))
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF91A9FF),
                            Color(0xFF3B88FF),
                            Color(0xFF267CFA),
                            Color(0xFF789BFF)
                        )
                    )
                )
        )
        // A faint inner highlight gives the bars the soft, glassy appearance in the reference.
        Box(
            modifier = Modifier
                .fillMaxWidth(0.48f)
                .fillMaxSize()
                .align(Alignment.Center)
                .padding(vertical = 12.dp)
                .clip(CircleShape)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.08f),
                            Color.White.copy(alpha = 0.0f),
                            Color.White.copy(alpha = 0.10f)
                        )
                    )
                )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun VoiceInputScreenPreview() {
    VoiceInputScreen()
}
