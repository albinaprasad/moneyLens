package com.moneyManager.moneylens.ui.StratergyScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moneyManager.moneylens.ui.commonUiElements.CircularPieChart
import com.moneyManager.moneylens.ui.commonUiElements.CommonButtonBlue
import com.moneyManager.moneylens.ui.commonUiElements.CommonTopBar
import com.moneyManager.moneylens.ui.commonUiElements.CustomDropDown
import kotlinx.coroutines.launch

@Composable
fun StrategyScreen(
    onCompleted: () -> Unit,
) {
    val viewModel: StrategyScreenViewModal = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .navigationBarsPadding()
            .statusBarsPadding()
            .padding(16.dp)
    ) {

        CommonTopBar("Strategy", showStartIcon = true)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            text = "Based on your expected income for the budget period, we recommend the following allocation:",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Pie Chart
        CircularPieChart(
            uiState.slices, 
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            text = "BUDGETING STRATEGY",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Dropdown
        CustomDropDown(
            options = uiState.availableStrategies,
            selectedOption = uiState.selectedStrategy,
            onOptionSelected = { selected ->
                viewModel.onStrategySelected(selected)
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Continue Button
        CommonButtonBlue("Continue") {
           coroutineScope.launch {
               viewModel.setStrategyScreenCompleted()
               onCompleted()
           }
        }
    }
}
