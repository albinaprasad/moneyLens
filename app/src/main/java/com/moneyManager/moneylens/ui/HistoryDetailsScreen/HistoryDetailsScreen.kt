package com.moneyManager.moneylens.ui.AnalysisAndHistoryScreen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.moneyManager.moneylens.ui.utils.formatAmountToTwoDecimal

private enum class DetailsPeriod {
    THIS_MONTH,
    LAST_MONTH,
    THIS_YEAR,
    CUSTOM
}

private data class SampleTransaction(
    val id: Int,
    val title: String,
    val subtitle: String,
    val amount: Double,
    val time: String,
    val icon: ImageVector,
    val color: Color
)

private data class TransactionGroup(
    val title: String,
    val total: Double,
    val transactions: List<SampleTransaction>
)

// Accent colors matching the History screen's category styling.
private val shoppingPink = Color(0xFFD45F65)
private val shoppingBlue = Color(0xFF3994D8)

private val shoppingGroups = listOf(
    TransactionGroup(
        title = "Today",
        total = 2850.0,
        transactions = listOf(
            SampleTransaction(
                1, "Amazon", "Online Shopping",
                1299.0, "5:24 PM",
                Icons.Default.ShoppingCart, shoppingBlue
            ),
            SampleTransaction(
                2, "Myntra", "Clothing",
                1550.0, "11:12 AM",
                Icons.Default.ShoppingBag, shoppingPink
            )
        )
    ),
    TransactionGroup(
        title = "Yesterday",
        total = 1320.0,
        transactions = listOf(
            SampleTransaction(
                3, "Flipkart", "Online Shopping",
                850.0, "7:40 PM",
                Icons.Default.ShoppingCart, shoppingBlue
            ),
            SampleTransaction(
                4, "Zudio", "Clothing",
                470.0, "2:15 PM",
                Icons.Default.ShoppingBag, shoppingPink
            )
        )
    ),
    TransactionGroup(
        title = "12 Oct 2026, Sun",
        total = 3260.0,
        transactions = listOf(
            SampleTransaction(
                5, "Amazon", "Online Shopping",
                2199.0, "6:30 PM",
                Icons.Default.ShoppingCart, shoppingBlue
            ),
            SampleTransaction(
                6, "Lifestyle", "Fashion",
                1061.0, "12:18 PM",
                Icons.Default.Checkroom, shoppingPink
            )
        )
    ),
    TransactionGroup(
        title = "10 Oct 2026, Fri",
        total = 1450.0,
        transactions = listOf(
            SampleTransaction(
                7, "Westside", "Fashion",
                1450.0, "4:22 PM",
                Icons.Default.Checkroom, shoppingPink
            )
        )
    )
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HistoryDetailsScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onTransactionClick: (Int) -> Unit = {}
) {
    var selectedPeriod by remember {
        mutableStateOf(DetailsPeriod.THIS_MONTH)
    }

    val total = when (selectedPeriod) {
        DetailsPeriod.THIS_MONTH -> 12450.0
        DetailsPeriod.LAST_MONTH -> 9850.0
        DetailsPeriod.THIS_YEAR -> 28450.0
        DetailsPeriod.CUSTOM -> 4850.0
    }

    val count = when (selectedPeriod) {
        DetailsPeriod.THIS_MONTH -> 15
        DetailsPeriod.LAST_MONTH -> 12
        DetailsPeriod.THIS_YEAR -> 42
        DetailsPeriod.CUSTOM -> 4
    }

    val percentage = when (selectedPeriod) {
        DetailsPeriod.THIS_MONTH -> 26
        DetailsPeriod.LAST_MONTH -> 19
        DetailsPeriod.THIS_YEAR -> 32
        DetailsPeriod.CUSTOM -> 12
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Shopping",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            actions = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More options"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 12.dp,
                bottom = 32.dp
            )
        ) {
            item {
                CategorySummaryCard(
                    total = total,
                    count = count,
                    percentage = percentage
                )

                Spacer(Modifier.height(24.dp))

                PeriodSelector(
                    selectedPeriod = selectedPeriod,
                    onPeriodSelected = { selectedPeriod = it }
                )

                Spacer(Modifier.height(30.dp))
            }

            items(
                items = shoppingGroups,
                key = { it.title }
            ) { group ->
                TransactionGroupSection(
                    group = group,
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
    percentage: Int
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
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
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(58.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(shoppingPink.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingBag,
                        contentDescription = "Shopping",
                        tint = shoppingPink,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Shopping",
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
                        text = "Total Spent",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        text = "₹${formatAmountToTwoDecimal(total)}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = shoppingPink,
                        maxLines = 1
                    )
                }
            }

            Spacer(Modifier.height(22.dp))

            LinearProgressIndicator(
                progress = { percentage / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(7.dp)
                    .clip(CircleShape),
                color = shoppingPink,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
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
    selectedPeriod: DetailsPeriod,
    onPeriodSelected: (DetailsPeriod) -> Unit
) {
    val periods = listOf(
        DetailsPeriod.THIS_MONTH,
        DetailsPeriod.LAST_MONTH,
        DetailsPeriod.THIS_YEAR,
        DetailsPeriod.CUSTOM
    )

    val labels = listOf(
        "Month",
        "Last Month",
        "Year",
        "Custom"
    )

    ButtonGroup(
        overflowIndicator = { /* No overflow button needed */ },
        modifier = Modifier.fillMaxWidth()
    ) {
        periods.forEachIndexed { index, period ->
            toggleableItem(
                checked = selectedPeriod == period,
                onCheckedChange = { checked ->
                    if (checked) onPeriodSelected(period)
                },
                label = labels[index],
                weight = 1f
            )
        }
    }
}

@Composable
private fun TransactionGroupSection(
    group: TransactionGroup,
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
                onClick = {
                    onTransactionClick(transaction.id)
                }
            )

            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun TransactionCard(
    transaction: SampleTransaction,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 14.dp,
                    end = 4.dp,
                    top = 14.dp,
                    bottom = 14.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(transaction.color.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = transaction.icon,
                    contentDescription = transaction.title,
                    tint = transaction.color,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = transaction.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = transaction.subtitle,
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
                    text = "− ₹${formatAmountToTwoDecimal(transaction.amount)}",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = transaction.time,
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