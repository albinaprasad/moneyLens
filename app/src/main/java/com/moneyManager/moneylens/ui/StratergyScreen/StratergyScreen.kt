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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moneyManager.moneylens.ui.commonUiElements.CircularPieChart
import com.moneyManager.moneylens.ui.commonUiElements.CommonButtonBlue
import com.moneyManager.moneylens.ui.commonUiElements.CommonTopBar
import com.moneyManager.moneylens.ui.commonUiElements.CustomDropDown
import com.moneyManager.moneylens.ui.theme.primary_06
import com.moneyManager.moneylens.ui.theme.white
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
            .background(white)
            .navigationBarsPadding()
            .statusBarsPadding()
            .padding(16.dp)
    ) {

        CommonTopBar("Strategy",true)

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(30.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = "Based on your expected income for the budget period, and using the 50/30/20 budgeting rule, we recommend:",
            fontSize = 12.sp,
            color = primary_06,
            textAlign = TextAlign.Left
        )

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(46.dp))

        //pie chart
        CircularPieChart(uiState.slices, modifier = Modifier
            .weight(1f)
            .align(Alignment.CenterHorizontally))

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(30.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = "BUDGETING  STRATEGY",
            fontSize = 12.sp,
            color = primary_06,
            textAlign = TextAlign.Left
        )

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(10.dp))

        //Drop down
        CustomDropDown(
            options = uiState.availableStrategies,
            selectedOption = uiState.selectedStrategy,
            onOptionSelected = { selected ->
                viewModel.onStrategySelected(selected)
            }, modifier = Modifier
        )

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(60.dp))

        //blue button
        CommonButtonBlue("Continue") {
           coroutineScope.launch {
               viewModel.setStrategyScreenCompleted()
               onCompleted()
           }
        }
    }
}
