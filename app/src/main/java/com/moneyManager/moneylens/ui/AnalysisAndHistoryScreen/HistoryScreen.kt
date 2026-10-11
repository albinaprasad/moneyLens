package com.moneyManager.moneylens.ui.AnalysisAndHistoryScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moneyManager.moneylens.DataClass.ChartSlice
import com.moneyManager.moneylens.ui.commonUiElements.CircularPieChart
import com.moneyManager.moneylens.ui.commonUiElements.CommonTabSelector
import com.moneyManager.moneylens.ui.commonUiElements.CommonTopBar

private val ScreenBg = Color(0xFF111416)
private val CardBg = Color(0xFF1E2129)
private val InnerBg = Color(0xFF171A20)
private val TextPrimary = Color(0xFFF5F5F7)
private val TextSecondary = Color(0xFF92949F)
private val SpendingColor = Color(0xFFE17B8B)
private val IncomeColor = Color(0xFF79C8A5)
private val ChartBlue = Color(0xFF819ACD)
private val Gold = Color(0xFFD9AF53)

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onTabClick: (String) -> Unit = {}
) {
    var selectedPeriod by remember { mutableStateOf("Year") }
    var selectedCategoryType by remember { mutableStateOf(0) }
    var selectedPaymentType by remember { mutableStateOf("Spending") }
    var selectedYear by remember { mutableIntStateOf(2026) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .background(ScreenBg)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 14.dp)
        ) {
            Spacer(Modifier.height(12.dp))

            // Header
            CommonTopBar(
                heading = "History",
                onStartClick = {onBackClick() }
            )

            Spacer(Modifier.height(14.dp))

            // Period selector
            SegmentedSelector(
                options = listOf("Week", "Month", "Year"),
                selected = selectedPeriod,
                onSelected = { selectedPeriod = it }
            )


            Spacer(Modifier.height(16.dp))

            // Year navigation
            Card(
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "‹",
                        fontSize = 32.sp,
                        color = TextPrimary,
                        modifier = Modifier.clickable { selectedYear-- }
                    )

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "$selectedYear",
                            color = TextPrimary,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "7 TRANSACTIONS",
                            color = TextSecondary,
                            fontSize = 10.sp
                        )
                    }

                    Text(
                        "›",
                        fontSize = 32.sp,
                        color = TextPrimary,
                        modifier = Modifier.clickable { selectedYear++ }
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // Spending and income summary
            SummaryCard()

            Spacer(Modifier.height(24.dp))

            SectionTitle("Categories")

            Spacer(Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CommonTabSelector(
                        tabs = listOf("Spending", "Income"),
                        selectedTab = selectedCategoryType,
                        onTabSelected = { selectedCategoryType = it }
                    )

                    Spacer(Modifier.height(6.dp))
                    val chartSlices = listOf(
                        ChartSlice(
                            name = "Food",
                            value = 15000f,
                            color = Color(0xFF819ACD)
                        ),
                        ChartSlice(
                            name = "Shopping",
                            value = 10000f,
                            color = Color(0xFFE17B8B)
                        ),
                        ChartSlice(
                            name = "Travel",
                            value = 5000f,
                            color = Color(0xFF79C8A5)
                        ),
                        ChartSlice(
                            name = "Bills",
                            value = 8000f,
                            color = Color(0xFFD9AF53)
                        ),
                        ChartSlice(
                            name = "Others",
                            value = 7000f,
                            color = Color(0xFFB39DDB)
                        )
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(0.85f),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularPieChart(
                            slices = chartSlices,
                            modifier = Modifier
                                .fillMaxWidth(0.75f)
                                .aspectRatio(1f)
                        )
                    }

                    Spacer(Modifier.height(26.dp))

                    //need to use the lazy row here later
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(InnerBg),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("•••", color = ChartBlue, fontSize = 16.sp)
                        }

                        Text(
                            "Others",
                            color = TextPrimary,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 12.dp)
                        )

                        Text(
                            if (selectedCategoryType == 0)
                                "₹3,00,000"
                            else
                                "₹51,55,22,935",
                            color = TextPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            SectionTitle("Payment modes")

            Spacer(Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg)
            ) {
                Column(Modifier.padding(16.dp)) {
                    SegmentedSelector(
                        options = listOf("Spending", "Income", "Transfers"),
                        selected = selectedPaymentType,
                        onSelected = { selectedPaymentType = it }
                    )

                    Spacer(Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("💵", fontSize = 22.sp)

                        Text(
                            when (selectedPaymentType) {
                                "Income" -> "Cash"
                                "Transfers" -> "Bank transfer"
                                else -> "Cash"
                            },
                            color = TextPrimary,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 12.dp)
                        )

                        Text(
                            when (selectedPaymentType) {
                                "Income" -> "₹3,00,000"
                                "Transfers" -> "₹0"
                                else -> "₹51,55,22,935"
                            },
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            SectionTitle("Stats")

            Spacer(Modifier.height(12.dp))

            StatsCard()

            Spacer(Modifier.height(20.dp))
        }
    }

@Composable
private fun SummaryCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg)
    ) {
        Column(Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(Modifier.weight(1f)) {
                    Text(
                        "SPENDING",
                        color = SpendingColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "₹51,55,22,935",
                        color = TextPrimary,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )
                }

                Spacer(Modifier.width(8.dp))

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        "INCOME",
                        color = IncomeColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "₹3,00,000",
                        color = TextPrimary,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )
                }
            }

            Spacer(Modifier.height(22.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(InnerBg)
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Net Balance",
                    color = TextSecondary,
                    fontSize = 12.sp,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    "-₹51,52,22,935",
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
private fun StatsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Text(
                "AVERAGE SPENDING",
                color = SpendingColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(12.dp))

            Row(Modifier.fillMaxWidth()) {
                StatItem(
                    label = "Per month",
                    value = "₹5,15,52,294",
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(8.dp))
                StatItem(
                    label = "Per transaction",
                    value = "₹8,59,20,489",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(20.dp))

            Text(
                "AVERAGE INCOME",
                color = IncomeColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(12.dp))

            Row(Modifier.fillMaxWidth()) {
                StatItem(
                    label = "Per month",
                    value = "₹30,000",
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(8.dp))
                StatItem(
                    label = "Per transaction",
                    value = "₹3,00,000",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun StatItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            label,
            color = TextSecondary,
            fontSize = 12.sp
        )
        Spacer(Modifier.height(6.dp))
        Text(
            value,
            color = TextPrimary,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1
        )
    }
}

@Composable
private fun SegmentedSelector(
    options: List<String>,
    selected: String,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .background(Color(0xFF111317))
            .padding(4.dp)
    ) {
        options.forEach { option ->
            val isSelected = selected == option

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(CircleShape)
                    .background(if (isSelected) Color(0xFF30343D) else Color.Transparent)
                    .clickable { onSelected(option) }
                    .padding(vertical = 10.dp, horizontal = 3.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = option,
                    color = if (isSelected) TextPrimary else TextSecondary,
                    fontSize = 11.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        color = TextPrimary,
        fontSize = 17.sp,
        fontWeight = FontWeight.Bold
    )
}

