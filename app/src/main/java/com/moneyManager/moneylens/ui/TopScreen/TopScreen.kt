package com.moneyManager.moneylens.ui.TopScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.moneyManager.moneylens.ui.Bottombar.BottomBar

@Composable
fun TopScreen(navController: NavHostController) {
    val viewModel  = hiltViewModel<TopViewModal>()
    val bottomNavItems by viewModel.bottomNavItems.collectAsState()
    Scaffold(
        bottomBar = {
            BottomBar(navController,bottomNavItems)
        }
    ) { padding ->
        TopScreenContents(modifier = Modifier.padding(padding))
    }

}


@Composable
fun TopScreenContents(modifier: Modifier){
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
