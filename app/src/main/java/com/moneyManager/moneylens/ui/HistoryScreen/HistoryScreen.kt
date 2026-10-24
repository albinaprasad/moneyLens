package com.moneyManager.moneylens.ui.HistoryScreen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moneyManager.moneylens.AnimationManager.AnimationManager.tabBounceAnimation
import com.moneyManager.moneylens.DataClass.AccountTotal
import com.moneyManager.moneylens.DataClass.ChartSlice
import com.moneyManager.moneylens.DataClass.HistoryUiState
import com.moneyManager.moneylens.DataClass.TimePeriod
import com.moneyManager.moneylens.ui.commonUiElements.CircularPieChart
import com.moneyManager.moneylens.ui.commonUiElements.CommonTabSelector
import com.moneyManager.moneylens.ui.commonUiElements.CommonTopBar
import com.moneyManager.moneylens.ui.utils.formatAmountToTwoDecimal
import com.moneyManager.moneylens.ui.utils.isLandscape
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onNavigateToHistoryDetails: (Int, String) -> Unit = { _, _ -> },
    viewModal: HistoryViewModal,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope

) {

    val uiState by viewModal.historyUiState.collectAsStateWithLifecycle()
    var selectedCategoryType by remember { mutableIntStateOf(0) }
    var selectedPaymentType by remember { mutableStateOf("Spending") }

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
                color = Color(category.color),
                icon = viewModal.getCategoryIcon(category.icon)
            )
        }

    val selectedAccountTotals =
        if (selectedPaymentType == "Spending") {
            uiState.accountExpenses
        } else {
            uiState.accountIncomes
        }


    val selectedCategories = if (selectedCategoryType == 0) {
        uiState.categoryExpenses
    } else {
        uiState.categoryIncomes
    }

    if (isLandscape()) {
        HistoryLandscapeMode(
            modifier = modifier,
            sharedTransitionScope = sharedTransitionScope,
            animatedVisibilityScope = animatedVisibilityScope,
            uiState = uiState,
            selectedCategoryType = selectedCategoryType,
            onCategoryTypeSelected = { selectedCategoryType = it },
            selectedPaymentType = selectedPaymentType,
            onPaymentTypeSelected = { selectedPaymentType = it },
            chartSlices = chartSlices,
            selectedAccountTotals = selectedAccountTotals,
            onBackClick = onBackClick,
            onTimePeriodSelected = { viewModal.onTimePeriodSelected(it) },
            onPreviousPeriod = { viewModal.onPreviousPeriod() },
            onNextPeriod = { viewModal.onNextPeriod() },
            getAccountIcon = { viewModal.getAccountIcon(it) },
            getAccountTint = { viewModal.getAccountTint(it) },
            onCategoryClick = { categoryName ->
                val category = selectedCategories.find {
                    it.categoryName == categoryName
                }

                if (category != null) {
                    val type = if (selectedCategoryType == 0) {
                        "EXPENSE"
                    } else {
                        "INCOME"
                    }

                    onNavigateToHistoryDetails(
                        category.categoryId,
                        type
                    )
                }
            }
        )
    } else {
        HistoryPortraitMode(
            modifier = modifier,
            sharedTransitionScope,
            animatedVisibilityScope,
            uiState = uiState,
            selectedCategoryType = selectedCategoryType,
            onCategoryTypeSelected = { selectedCategoryType = it },
            selectedPaymentType = selectedPaymentType,
            onPaymentTypeSelected = { selectedPaymentType = it },
            chartSlices = chartSlices,
            selectedAccountTotals = selectedAccountTotals,
            onBackClick = onBackClick,
            onTimePeriodSelected = { viewModal.onTimePeriodSelected(it) },
            onPreviousPeriod = { viewModal.onPreviousPeriod() },
            onNextPeriod = { viewModal.onNextPeriod() },
            getAccountIcon = { viewModal.getAccountIcon(it) },
            getAccountTint = { viewModal.getAccountTint(it) },
            onCategoryClick = { categoryName ->
                val category = selectedCategories.find {
                    it.categoryName == categoryName
                }

                if (category != null) {
                    val type = if (selectedCategoryType == 0) {
                        "EXPENSE"
                    } else {
                        "INCOME"
                    }

                    onNavigateToHistoryDetails(
                        category.categoryId,
                        type
                    )
                }
            }
        )
    }
}

@Composable
private fun HistoryPortraitMode(
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    uiState: HistoryUiState,
    selectedCategoryType: Int,
    onCategoryTypeSelected: (Int) -> Unit,
    selectedPaymentType: String,
    onPaymentTypeSelected: (String) -> Unit,
    chartSlices: List<ChartSlice>,
    selectedAccountTotals: List<AccountTotal>,
    onBackClick: () -> Unit,
    onTimePeriodSelected: (TimePeriod) -> Unit,
    onPreviousPeriod: () -> Unit,
    onNextPeriod: () -> Unit,
    getAccountIcon: (String) -> ImageVector,
    getAccountTint: (String) -> Color,
    onCategoryClick: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(12.dp))

        HistoryHeaderSection(
            uiState = uiState,
            onBackClick = onBackClick,
            onTimePeriodSelected = onTimePeriodSelected,
            onPreviousPeriod = onPreviousPeriod,
            onNextPeriod = onNextPeriod
        )

        Spacer(Modifier.height(24.dp))

        SummaryCard(
            totalExpenses = uiState.totalExpenses,
            totalIncome = uiState.totalIncome
        )

        Spacer(Modifier.height(24.dp))

        CategoriesSection(
            sharedTransitionScope,
            animatedVisibilityScope,
            selectedCategoryType = selectedCategoryType,
            onCategoryTypeSelected = onCategoryTypeSelected,
            chartSlices = chartSlices,
            onCategoryClick = onCategoryClick

        )

        Spacer(Modifier.height(24.dp))

        PaymentModesSection(
            selectedPaymentType = selectedPaymentType,
            onPaymentTypeSelected = onPaymentTypeSelected,
            selectedAccountTotals = selectedAccountTotals,
            getAccountIcon = getAccountIcon,
            getAccountTint = getAccountTint
        )

        Spacer(Modifier.height(24.dp))

        StatsSection(uiState = uiState)

        Spacer(Modifier.height(50.dp))
    }
}

@Composable
private fun HistoryLandscapeMode(
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    uiState: HistoryUiState,
    selectedCategoryType: Int,
    onCategoryTypeSelected: (Int) -> Unit,
    selectedPaymentType: String,
    onPaymentTypeSelected: (String) -> Unit,
    chartSlices: List<ChartSlice>,
    selectedAccountTotals: List<AccountTotal>,
    onBackClick: () -> Unit,
    onTimePeriodSelected: (TimePeriod) -> Unit,
    onPreviousPeriod: () -> Unit,
    onNextPeriod: () -> Unit,
    getAccountIcon: (String) -> ImageVector,
    getAccountTint: (String) -> Color,
    onCategoryClick: (String) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        // Left pane – Header, Period Selector, Summary Card, Stats
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(end = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(12.dp))

            HistoryHeaderSection(
                uiState = uiState,
                onBackClick = onBackClick,
                onTimePeriodSelected = onTimePeriodSelected,
                onPreviousPeriod = onPreviousPeriod,
                onNextPeriod = onNextPeriod
            )

            Spacer(Modifier.height(24.dp))

            SummaryCard(
                totalExpenses = uiState.totalExpenses,
                totalIncome = uiState.totalIncome
            )

            Spacer(Modifier.height(24.dp))

            StatsSection(uiState = uiState)

            Spacer(Modifier.height(24.dp))
        }

        // Right pane – Categories and Payment Modes
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(start = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(12.dp))

            CategoriesSection(
                sharedTransitionScope,
                animatedVisibilityScope,
                selectedCategoryType = selectedCategoryType,
                onCategoryTypeSelected = onCategoryTypeSelected,
                chartSlices = chartSlices,
                onCategoryClick
            )

            Spacer(Modifier.height(24.dp))

            PaymentModesSection(
                selectedPaymentType = selectedPaymentType,
                onPaymentTypeSelected = onPaymentTypeSelected,
                selectedAccountTotals = selectedAccountTotals,
                getAccountIcon = getAccountIcon,
                getAccountTint = getAccountTint
            )

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun HistoryHeaderSection(
    uiState: HistoryUiState,
    onBackClick: () -> Unit,
    onTimePeriodSelected: (TimePeriod) -> Unit,
    onPreviousPeriod: () -> Unit,
    onNextPeriod: () -> Unit
) {
    CommonTopBar(
        heading = "History",
        onStartClick = { onBackClick() }
    )

    Spacer(Modifier.height(14.dp))

    SegmentedSelector(
        options = listOf("Week", "Month", "Year"),
        selected = uiState.timePeriod.name
            .lowercase()
            .replaceFirstChar { it.uppercase() },
        onSelected = { selected ->
            val period = TimePeriod.valueOf(selected.uppercase())
            onTimePeriodSelected(period)
        }
    )

    Spacer(Modifier.height(16.dp))

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
                    onPreviousPeriod()
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
                    onNextPeriod()
                }
            )
        }
    }
}

@Composable
private fun CategoriesSection(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    selectedCategoryType: Int,
    onCategoryTypeSelected: (Int) -> Unit,
    chartSlices: List<ChartSlice>,
    onCategoryClick: (String) -> Unit
) {
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
                onTabSelected = onCategoryTypeSelected
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
                        .aspectRatio(1f),
                    false
                )
            }

            Spacer(Modifier.height(26.dp))

            if (chartSlices.isEmpty()) {
                Text(
                    text = "No category data for this period",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            } else {
                chartSlices.forEach { slice ->
                    Row(
                        modifier = with(sharedTransitionScope) {
                            Modifier
                                .fillMaxWidth()
                                .sharedBounds(
                                    sharedContentState = rememberSharedContentState(
                                        key = "category-container-${slice.name}"
                                    ),
                                    animatedVisibilityScope = animatedVisibilityScope,
                                    boundsTransform = { initialBounds, targetBounds ->
                                        tween(
                                            durationMillis = 500,
                                            easing = FastOutSlowInEasing
                                        )
                                    }
                                )
                        }.clickable {
                            onCategoryClick(slice.name)
                        },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(slice.color.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = slice.icon ?: Icons.Default.MoreHoriz,
                                contentDescription = slice.name,
                                tint = slice.color,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Text(
                            slice.name,
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 12.dp)
                        )

                        Text(
                            text = "₹${formatAmountToTwoDecimal(slice.value.toDouble())}",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun PaymentModesSection(
    selectedPaymentType: String,
    onPaymentTypeSelected: (String) -> Unit,
    selectedAccountTotals: List<AccountTotal>,
    getAccountIcon: (String) -> ImageVector,
    getAccountTint: (String) -> Color
) {
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
                onSelected = onPaymentTypeSelected
            )

            Spacer(Modifier.height(20.dp))

            if (selectedAccountTotals.isEmpty()) {
                Text(
                    text = "No payment mode data for this period",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            } else {
                selectedAccountTotals.forEach { account ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = getAccountIcon(account.accountIcon),
                            contentDescription = account.accountName,
                            tint = getAccountTint(account.accountName),
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
    }
}

@Composable
private fun StatsSection(uiState: HistoryUiState) {
    SectionTitle("Stats")

    Spacer(Modifier.height(12.dp))

    StatsCard(uiState)
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
                    .tabBounceAnimation(selected = isSelected)
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
