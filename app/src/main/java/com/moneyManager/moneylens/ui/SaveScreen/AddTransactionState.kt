
package com.moneyManager.moneylens.ui.SaveScreen

import java.time.LocalDate
import java.time.LocalTime

enum class TransactionType(val label: String) {
    EXPENSE("Expense"),
    INCOME("Income"),

}

data class AddTransactionState(
    val type: TransactionType = TransactionType.EXPENSE,
    val amount: String = "0",
    val category: String = "Others",
    val paymentMode: String = "Cash",
    val note: String = "",
    val date: LocalDate = LocalDate.now(),
    val time: LocalTime = LocalTime.now()
)