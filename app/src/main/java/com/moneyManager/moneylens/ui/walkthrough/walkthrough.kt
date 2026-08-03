package com.moneyManager.moneylens.ui.walkthrough

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.moneyManager.moneylens.ui.commonUiElements.CommonButtonBlue
import com.moneyManager.moneylens.ui.utils.DeviceOrientation
import com.moneyManager.moneylens.ui.utils.LocalDeviceOrientation
import kotlinx.coroutines.launch

@Composable
fun WalkThrough(onWalkThroughCompleted: () -> Unit) {
    val viewModel: WalkThroughViewmodel = hiltViewModel()
    val pages by viewModel.pages.collectAsState()
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val coroutineScope = rememberCoroutineScope()
    val isLandscape = LocalDeviceOrientation.current == DeviceOrientation.LANDSCAPE

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { index ->
            walkThroughPages(pages[index])
        }

        Spacer(modifier = Modifier.height(if (isLandscape) 8.dp else 24.dp))

        WormPagerIndicator(
            pagerState = pagerState,
            pageCount = pages.size,
            activeColor = MaterialTheme.colorScheme.primary,
            inactiveColor = MaterialTheme.colorScheme.surfaceVariant
        )

        CommonButtonBlue(
            text = if (pagerState.currentPage < pages.lastIndex) "Next" else "Get Started"
        ) {
            coroutineScope.launch {
                if (pagerState.currentPage < pages.lastIndex) {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                } else {
                    viewModel.setWalkThroughCompleted()
                    onWalkThroughCompleted()
                }
            }
        }

        Spacer(modifier = Modifier.height(if (isLandscape) 4.dp else 16.dp))
    }
}
