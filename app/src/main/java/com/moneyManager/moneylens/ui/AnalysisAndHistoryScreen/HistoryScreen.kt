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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moneyManager.moneylens.DataClass.ChartSlice
import com.moneyManager.moneylens.DataClass.HistoryUiState
import com.moneyManager.moneylens.DataClass.TimePeriod
import com.moneyManager.moneylens.ui.commonUiElements.CircularPieChart
import com.moneyManager.moneylens.ui.commonUiElements.CommonTabSelector
import com.moneyManager.moneylens.ui.commonUiElements.CommonTopBar
import com.moneyManager.moneylens.ui.utils.formatAmountToTwoDecimal
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onTabClick: (String) -> Unit = {}
) {
    val viewModal: HistoryViewModal = hiltViewModel()
    val uiState by viewModal.historyUiState.collectAsStateWithLifecycle()
    var selectedCategoryType by remember { mutableStateOf(0) }
    var selectedPaymentType by remember { mutableStateOf("Spending") }

    val totalExpenses = uiState.totalExpenses
    val totalIncome = uiState.totalIncome
    val chartSlices = (
            if (selectedCategoryType == 0) {
                uiState.categoryExpenses
            } else {
                uiState.categoryIncomes
            }
            ).map { category ->
            ChartSlice(
                name = category.categoryName,
                value = category.totalAmount.toFloat(),
                color = Color(category.color)
            )
        }

    val selectedAccountTotals =
        if (selectedPaymentType == "Spending") {
            uiState.accountExpenses
        } else {
            uiState.accountIncomes
        }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(12.dp))

        // Header
        CommonTopBar(
            heading = "History",
            onStartClick = { onBackClick() }
        )

        Spacer(Modifier.height(14.dp))

        // Period selector
        SegmentedSelector(
            options = listOf("Week", "Month", "Year"),
            selected = uiState.timePeriod.name
                .lowercase()
                .replaceFirstChar { it.uppercase() },
            onSelected = { selected ->
                val period = TimePeriod.valueOf(selected.uppercase())
                viewModal.onTimePeriodSelected(period)
            }
        )

        Spacer(Modifier.height(16.dp))

        // Year navigation card
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "‹",
                    fontSize = 32.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.clickable {
                        viewModal.onPreviousPeriod()
                    }
                )

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = getPeriodLabel(
                            uiState.timePeriod,
                            uiState.selectedDate
                        ),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "${uiState.transactions.size} TRANSACTIONS",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Text(
                    "›",
                    fontSize = 32.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.clickable {
                        viewModal.onNextPeriod()
                    }
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        // Spending and income summary
        SummaryCard(totalExpenses,totalIncome)

        Spacer(Modifier.height(24.dp))

        SectionTitle("Categories")

        Spacer(Modifier.height(12.dp))

        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CommonTabSelector(
                    tabs = listOf("Spending", "Income"),
                    selectedTab = selectedCategoryType,
                    onTabSelected = { selectedCategoryType = it }
                )

                Spacer(Modifier.height(6.dp))

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
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "•••",
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 16.sp
                        )
                    }

                    Text(
                        "Others",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 12.dp)
                    )

                    Text(
                        if (selectedCategoryType == 0)
                            "₹3,00,000"
                        else
                            "₹51,55,22,935",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        SectionTitle("Payment modes")

        Spacer(Modifier.height(12.dp))

        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(Modifier.padding(20.dp)) {
                SegmentedSelector(
                    options = listOf("Spending", "Income"),
                    selected = selectedPaymentType,
                    onSelected = { selectedPaymentType = it }
                )

                Spacer(Modifier.height(20.dp))

                selectedAccountTotals.forEach { account ->

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = viewModal.getAccountIcon(account.accountIcon),
                            contentDescription = account.accountName,
                            tint = viewModal.getAccountTint(account.accountName),
                            modifier = Modifier.size(22.dp)
                        )

                        Text(
                            text = account.accountName,
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 12.dp)
                        )

                        Text(
                            text = "₹${formatAmountToTwoDecimal(account.totalAmount)}",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(Modifier.height(16.dp))
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        SectionTitle("Stats")

        Spacer(Modifier.height(12.dp))

        StatsCard(uiState)

        Spacer(Modifier.height(50.dp))
    }
}

@Composable
private fun SummaryCard(totalExpenses: Double, totalIncome: Double) {

    val netBalance = totalIncome - totalExpenses
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(Modifier.weight(1f)) {
                    Text(
                        "SPENDING",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "₹${formatAmountToTwoDecimal(totalExpenses)}",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
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
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "₹${formatAmountToTwoDecimal(totalIncome)}",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Net Balance",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    formatAmountToTwoDecimal(netBalance),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun StatsCard(
    uiState: HistoryUiState
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                "AVERAGE SPENDING",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.error,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(12.dp))

            Row(Modifier.fillMaxWidth()) {
                StatItem(
                    label = "Total Expenses",
                    value = "₹${formatAmountToTwoDecimal(uiState.totalExpenses)}",
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(8.dp))
                StatItem(
                    label = "Highest Expense",
                    value = "₹${formatAmountToTwoDecimal(uiState.highestExpense)}",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(20.dp))

            Text(
                "AVERAGE INCOME",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(12.dp))

            Row(Modifier.fillMaxWidth()) {
                StatItem(
                    label = "Total Income",
                    value = "₹${formatAmountToTwoDecimal(uiState.totalIncome)}",
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(8.dp))
                StatItem(
                    label = "Transactions",
                    value = uiState.transactionCount.toString(),
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
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(6.dp))
        Text(
            value,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
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
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f))
            .padding(4.dp)
    ) {
        options.forEach { option ->
            val isSelected = selected == option

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        if (isSelected) MaterialTheme.colorScheme.primary
                        else Color.Transparent
                    )
                    .clickable { onSelected(option) }
                    .padding(vertical = 10.dp, horizontal = 3.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = option,
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelMedium,
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
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.Bold
    )
}

private fun getPeriodLabel(
    period: TimePeriod,
    date: LocalDate
): String {
    return when (period) {
        TimePeriod.WEEK -> {
            val start = date.with(
                TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)
            )
            val end = start.plusDays(6)

            "${start.dayOfMonth} ${start.month.name.lowercase()
                .replaceFirstChar { it.uppercase() }} – " +
                    "${end.dayOfMonth} ${end.month.name.lowercase()
                        .replaceFirstChar { it.uppercase() }} ${end.year}"
        }

        TimePeriod.MONTH ->
            date.format(DateTimeFormatter.ofPattern("MMMM yyyy"))

        TimePeriod.YEAR ->
            date.year.toString()
    }


}