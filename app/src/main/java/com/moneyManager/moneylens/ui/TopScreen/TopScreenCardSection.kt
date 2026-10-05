package com.moneyManager.moneylens.ui.TopScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.text.NumberFormat
import java.util.Locale

private fun formatCurrency(amount: Double): String {
    val formatter = NumberFormat.getNumberInstance(Locale.US)
    return if (amount < 0) {
        "-₹${formatter.format(-amount.toLong())}"
    } else {
        "₹${formatter.format(amount.toLong())}"
    }
}

@Composable
fun TopScreenCardSection(
    totalBalance: Double = 0.0,
    totalIncome: Double = 0.0,
    totalExpenses: Double = 0.0
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        InfoCard(
            title = "Total Balance",
            value = formatCurrency(totalBalance),
            modifier = Modifier
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InfoCard(
                title = "Income",
                value = formatCurrency(totalIncome),
                modifier = Modifier.weight(1f)
            )

            InfoCard(
                title = "Expenses",
                value = formatCurrency(totalExpenses),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun InfoCard(title: String, value: String, modifier: Modifier) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun previewCardSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        TopScreenCardSection(
            totalBalance = 45200.0,
            totalIncome = 12500.0,
            totalExpenses = 8420.0
        )
    }
}
