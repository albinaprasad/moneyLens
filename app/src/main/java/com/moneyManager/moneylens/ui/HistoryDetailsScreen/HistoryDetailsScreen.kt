package com.moneyManager.moneylens.ui.AnalysisAndHistoryScreen


import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moneyManager.moneylens.DataClass.TimePeriod
import com.moneyManager.moneylens.DataClass.TransactionGroup
import com.moneyManager.moneylens.database.Entity.Transaction
import com.moneyManager.moneylens.ui.HistoryScreen.HistoryViewModal
import com.moneyManager.moneylens.ui.commonUiElements.CommonTopBar
import com.moneyManager.moneylens.ui.theme.GreenText
import com.moneyManager.moneylens.ui.utils.formatAmountToTwoDecimal
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters


// Accent colors matching the History screen's category styling.

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HistoryDetailsScreen(
    modifier: Modifier = Modifier,
    viewModal: HistoryViewModal,
    categoryId: Int,
    categoryType: String,
    onBackClick: () -> Unit,
    onTransactionClick: (Int) -> Unit = {},
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {

    val uiState by viewModal.historyUiState.collectAsStateWithLifecycle()
    val categoryTransactions = uiState.transactions.filter { transaction ->
        transaction.categoryId == categoryId && transaction.type == categoryType
    }

    // each category total amount
    val categoryTotalAmount = categoryTransactions.sumOf { transaction ->
        transaction.amount
    }

    var selectedPeriod by remember {
        mutableStateOf(TimePeriod.MONTH)
    }

    val count = categoryTransactions.size

    // Total amount spend or recieved
    val overallTotal = if (categoryType == "EXPENSE") {
        uiState.totalExpenses
    } else {
        uiState.totalIncome
    }

    val percentage = if (overallTotal > 0.0) {
        ((categoryTotalAmount / overallTotal) * 100).toInt()
    } else {
        0
    }

    val selectedCategory = (if (categoryType == "EXPENSE") {
        uiState.categoryExpenses
    } else {
        uiState.categoryIncomes
    }).find {
        it.categoryId == categoryId
    }

    val transactionGroups = categoryTransactions
        .groupBy { transaction ->
            val date = Instant.ofEpochMilli(transaction.date)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()

            when (selectedPeriod) {
                TimePeriod.WEEK ->
                    date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))

                TimePeriod.MONTH -> date.withDayOfMonth(1)

                TimePeriod.YEAR -> date.withDayOfYear(1)
            }
        }
        .toSortedMap(compareByDescending { it })
        .map { (date, transactions) ->
            TransactionGroup(
                title = getPeriodLabel(selectedPeriod, date),
                transactions = transactions
            )
        }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
    ) {
        CommonTopBar(
            heading = "History Details ", onStartClick = { onBackClick() })

        LazyColumn(
            modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(
                start = 16.dp, end = 16.dp, top = 12.dp, bottom = 32.dp
            )
        ) {
            item {
                CategorySummaryCard(
                    total = categoryTotalAmount,
                    count = count,
                    percentage = percentage,
                    categoryType = categoryType,
                    categoryName = selectedCategory?.categoryName ?: "Category",
                    categoryIcon = viewModal.getCategoryIcon(
                        selectedCategory?.icon.orEmpty()
                    ),
                    categoryColor = Color(selectedCategory?.color ?: android.graphics.Color.GRAY),
                    sharedTransitionScope = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope
                )

                Spacer(Modifier.height(24.dp))

                PeriodSelector(
                    selectedPeriod = selectedPeriod, onPeriodSelected = {
                        selectedPeriod = it
                    })

                Spacer(Modifier.height(30.dp))
            }

            items(
                items = transactionGroups, key = { it.title }) { group ->
                TransactionGroupSection(
                    group = group,
                    categoryName = selectedCategory?.categoryName ?: "Unknown",
                    categoryIcon = viewModal.getCategoryIcon(
                        selectedCategory?.icon.orEmpty()
                    ),
                    categoryColor = Color(
                        selectedCategory?.color ?: android.graphics.Color.GRAY
                    ),
                    onTransactionClick = onTransactionClick
                )

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun CategorySummaryCard(
    total: Double,
    count: Int,
    percentage: Int,
    categoryName: String,
    categoryIcon: ImageVector,
    categoryColor: Color,
    categoryType: String,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    ElevatedCard(
        modifier = with(sharedTransitionScope) {
            Modifier
                .fillMaxWidth()
                .sharedBounds(
                    sharedContentState = rememberSharedContentState(
                        key = "category-container-$categoryName"
                    ),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = { initialBounds, targetBounds ->
                        tween(
                            durationMillis = 500,
                            easing = FastOutSlowInEasing
                        )
                    }
                )
        },
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(58.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(categoryColor.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = categoryIcon,
                        contentDescription = "Shopping",
                        tint = categoryColor,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = categoryName,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        text = "$count transactions",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(Modifier.width(6.dp))

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = if (categoryType == "EXPENSE") "Total Spent" else "Total Received",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        text = "₹${formatAmountToTwoDecimal(total)}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = categoryColor,
                        maxLines = 1
                    )
                }
            }

            Spacer(Modifier.height(22.dp))

            GradientProgressBar(
                percentage = percentage / 100f  ,
                categoryColor

            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "$percentage% of total spending",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun PeriodSelector(
    selectedPeriod: TimePeriod, onPeriodSelected: (TimePeriod) -> Unit
) {

    val periods = listOf(
        TimePeriod.WEEK, TimePeriod.MONTH, TimePeriod.YEAR
    )

    val labels = listOf(
        "Week",
        "Month",
        "Year",
    )

    ButtonGroup(
        overflowIndicator = { }, modifier = Modifier.fillMaxWidth()
    ) {
        periods.forEachIndexed { index, period ->
            toggleableItem(
                checked = selectedPeriod == period, onCheckedChange = { checked ->
                    if (checked) onPeriodSelected(period)
                }, label = labels[index], weight = 1f
            )
        }
    }
}

@Composable
private fun TransactionGroupSection(
    group: TransactionGroup,
    categoryName: String,
    categoryIcon: ImageVector,
    categoryColor: Color,
    onTransactionClick: (Int) -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = group.title,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "₹${formatAmountToTwoDecimal(group.total)}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        group.transactions.forEach { transaction ->
            TransactionCard(
                transaction = transaction,
                categoryName = categoryName,
                categoryIcon = categoryIcon,
                categoryColor = categoryColor,
                onClick = {
                    onTransactionClick(transaction.id)
                })

            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun TransactionCard(
    transaction: Transaction,
    onClick: () -> Unit,
    categoryIcon: ImageVector,
    categoryColor: Color,
    categoryName: String,
) {
    val transactionTime =
        java.time.Instant.ofEpochMilli(transaction.date).atZone(java.time.ZoneId.systemDefault())
            .format(
                java.time.format.DateTimeFormatter.ofPattern("hh:mm a")
            )

    val transactionDate =
        java.time.Instant.ofEpochMilli(transaction.date).atZone(java.time.ZoneId.systemDefault())
            .format(
                java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy")
            )


    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(22.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 14.dp, end = 4.dp, top = 14.dp, bottom = 14.dp
                ), verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(categoryColor.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = categoryIcon,
                    contentDescription = null,
                    tint = categoryColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                if (transaction.note.isNotBlank()) {
                    Text(
                        text = transaction.note,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(Modifier.height(4.dp))

                    Text(
                        text = transactionDate,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

            }

            Spacer(Modifier.width(6.dp))

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "${if (transaction.type == "EXPENSE") "−" else "+"} ₹${
                        formatAmountToTwoDecimal(transaction.amount)
                    }",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = if (transaction.type == "EXPENSE") {
                        Color.Red
                    } else {
                        GreenText
                    },
                    maxLines = 1
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = transactionTime,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Transaction options",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun GradientProgressBar(
    percentage: Float, categoryColor: Color, modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(7.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(percentage)
                .fillMaxHeight()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            categoryColor.copy(alpha = 0.45f), categoryColor, Color(
                                red = categoryColor.red * 0.75f,
                                green = categoryColor.green * 0.75f,
                                blue = categoryColor.blue * 0.75f,
                                alpha = categoryColor.alpha
                            )
                        )
                    ), shape = CircleShape
                )
        )
    }
}

private fun getPeriodLabel(
    period: TimePeriod,
    date: LocalDate
): String {
    return when (period) {
        TimePeriod.WEEK -> {
            val start = date.with(
                TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)
            )
            val end = start.plusDays(6)

            "${start.format(DateTimeFormatter.ofPattern("dd MMM"))} – " +
                    end.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
        }

        TimePeriod.MONTH ->
            date.format(DateTimeFormatter.ofPattern("MMMM yyyy"))

        TimePeriod.YEAR ->
            date.year.toString()
    }
}