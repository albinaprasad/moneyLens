package com.moneyManager.moneylens.DataClass

import com.moneyManager.moneylens.database.Entity.Transaction
import java.time.LocalDate


enum class TimePeriod{
    WEEK,
    MONTH,
    YEAR
}
data class HistoryUiState(
    val timePeriod: TimePeriod = TimePeriod.MONTH,
    val selectedDate: LocalDate = LocalDate.now(), //for switching teh week,year ,month tab refelctions
    val startDate: Long = 0L, // for transation retrival based on date
    val endDate: Long = 0L, // for transation retrival based on date
    val transactions: List<Transaction> = emptyList(),
    val totalIncome: Double = 0.0,
    val totalExpenses: Double = 0.0,
    val categoryExpenses: List<CategoryExpense> = emptyList(),
    val categoryIncomes: List<CategoryExpense> = emptyList(),
    val accountExpenses: List<AccountTotal> = emptyList(),
    val accountIncomes: List<AccountTotal> = emptyList()
){

}

// data class used for showing the progress pie chart in the history screen
data class CategoryExpense(
    val categoryId: Int,
    val categoryName: String,
    val icon: String,
    val color: Int,
    val totalAmount: Double
)

//data class used for showing the payment mode section
data class AccountTotal(
    val accountId: Int,
    val accountIcon: String,
    val accountName: String,
    val totalAmount: Double
)

