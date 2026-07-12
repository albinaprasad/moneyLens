package com.moneyManager.moneylens.ui.splashScreen

import android.R.attr.maxHeight
import android.R.attr.maxWidth
import android.R.interpolator.bounce
import android.graphics.fonts.Font
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moneyManager.moneylens.R
import com.moneyManager.moneylens.ui.theme.myFont
import com.moneyManager.moneylens.ui.theme.primary_09
import com.moneyManager.moneylens.ui.theme.white
import kotlinx.coroutines.delay
import kotlin.math.log

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(color = white),
        contentAlignment = Alignment.Center
    ) {
        val density = LocalDensity.current
        val screenWidthPx = with(density) { maxWidth.toPx() }
        val screenHeightPx = with(density) { maxHeight.toPx() }
        val iconSizePx = with(density) { 21.dp.toPx() }

        val targetScale = remember {
            val screenDiagonal = kotlin.math.sqrt(screenWidthPx * screenWidthPx + screenHeightPx * screenHeightPx)
            screenDiagonal / iconSizePx
        }

        val logoScale = remember { Animatable(0f) }
        val alphaScale = remember { Animatable(0f) }

        LaunchedEffect(Unit) {
            logoScale.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            delay(500)
            logoScale.animateTo(
                targetValue = targetScale,
                animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
            )
            alphaScale.animateTo(
                1f,
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            )
            delay(300)
            onSplashFinished()
        }

        Image(
            painter = painterResource(R.drawable.ic_dot),
            contentDescription = null,
            modifier = Modifier
                .size(21.dp)
                .scale(logoScale.value)
        )

        LogoPart(alphaScale.value)
    }
}

@Composable
fun LogoPart( alphaScale: Float){

    Column(modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .alpha(alphaScale)
        )
        Spacer(Modifier.fillMaxWidth().height(20.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .alpha(alphaScale),
            text = "MONEY LENS",
            color = primary_09,
            fontSize = 24.sp,
            fontFamily = myFont,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,

        )
    }
}