package com.moneyManager.moneylens.ui.StratergyScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moneyManager.moneylens.DataClass.ChartSlice
import com.moneyManager.moneylens.ui.TopScreen.TopBar
import com.moneyManager.moneylens.ui.commonUiElements.CircularPieChart
import com.moneyManager.moneylens.ui.commonUiElements.CommonButtonBlue
import com.moneyManager.moneylens.ui.commonUiElements.CommonTopBar
import com.moneyManager.moneylens.ui.commonUiElements.CustomDropDown
import com.moneyManager.moneylens.ui.theme.primary_06
import com.moneyManager.moneylens.ui.theme.white

@Composable
fun StrategyScreen(
//    onCompleted: () -> Unit,
    viewModel: StrategyScreenViewModal = hiltViewModel()
) {
    val dynamicChartSlices = listOf(
        ChartSlice(name = "Housing", value = 35f, color = Color(0xFFEF4444)),    // Red
        ChartSlice(name = "Groceries", value = 25f, color = Color(0xFF3B82F6)),  // Blue
        ChartSlice(name = "Entertainment", value = 20f, color = Color(0xFF10B981)), // Green
        ChartSlice(name = "Investments", value = 20f, color = Color(0xFF8B5CF6))  // Purple
    )
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

        CircularPieChart(dynamicChartSlices, modifier = Modifier.weight(1f).align(Alignment.CenterHorizontally))

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

        CustomDropDown(modifier = Modifier)

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(60.dp))

        CommonButtonBlue("Continue") { }
    }
}

@Composable
@Preview
fun strat(){
     StrategyScreen()

}
