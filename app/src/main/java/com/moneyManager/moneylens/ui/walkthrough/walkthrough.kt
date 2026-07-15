package com.moneyManager.moneylens.ui.walkthrough

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.moneyManager.moneylens.ui.theme.primary_09
import com.moneyManager.moneylens.ui.theme.white
import kotlinx.coroutines.launch

@Composable
fun WalkThrough(onWalkThroughCompleted: () -> Unit) {
    val viewModel: WalkThroughViewmodel = hiltViewModel()
    val pages by viewModel.pages.collectAsState()
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = white)
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { index ->
            walkThroughPages(pages[index])
        }


        Spacer(modifier = Modifier.height(24.dp))

        WormPagerIndicator(
            pagerState = pagerState,
            pageCount = pages.size
        )

        Button(
            onClick = {
                coroutineScope.launch {
                    if (pagerState.currentPage < pages.lastIndex) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    } else {
                        viewModel.setWalkThroughCompleted()
                        onWalkThroughCompleted()
                    }
                }
            },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primary_09),
            modifier = Modifier
                .padding(horizontal = 36.dp, vertical = 24.dp)
                .fillMaxWidth()
                .height(51.dp)
                .dropShadow(
                    shape = RoundedCornerShape(16.dp),
                    shadow = Shadow(
                        radius = 16.dp,
                        spread = 0.dp,
                        color = Color.Black.copy(alpha = 0.35f),
                        offset = DpOffset(0.dp, 6.dp)
                    )
                )
        ) {
            Text("Continue")
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}