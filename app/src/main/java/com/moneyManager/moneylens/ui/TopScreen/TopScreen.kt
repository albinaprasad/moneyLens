package com.moneyManager.moneylens.ui.TopScreen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.moneyManager.moneylens.AnimationManager.AnimationManager.customFadingAnimation
import com.moneyManager.moneylens.AnimationManager.AnimationManager.customScalingAnimation
import com.moneyManager.moneylens.R
import com.moneyManager.moneylens.ui.Bottombar.BottomBar

@Composable
fun TopScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<TopViewModal>()
    val bottomNavItems by viewModel.bottomNavItems.collectAsState()

    Scaffold(
        bottomBar = {
            BottomBar(navController, bottomNavItems){

            }
        },
        // floating button to add money
        floatingActionButton = {
            TopScreenFAB()
        }
    ) { padding ->
        TopScreenContents()
    }

}


@Composable
fun TopScreenContents() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Top Bar
        TopBar()

        // Cards Section
        TopScreenCardSection()

        // Budget Progress Section
        BudgetProgressCard()
    }
}

@Composable
fun TopScreenFAB(){
    var isToggled by remember { mutableStateOf(false) }
    var isAppeared by remember { mutableStateOf(false) }

    val animatedCorner by animateDpAsState(
        targetValue = if (isToggled) 28.dp else 12.dp,
        label = "cornerAnimation"
    )

    LaunchedEffect(Unit) {
        isAppeared = true
    }

        FloatingActionButton(
            onClick = {
                isToggled = !isToggled
            },
            modifier = Modifier.customScalingAnimation(isAppeared)
                .customFadingAnimation(isAppeared),
            shape = RoundedCornerShape(animatedCorner),
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ) {
            Icon(painter = painterResource(R.drawable.ic_add), contentDescription = null)
        }

}