package com.moneyManager.moneylens.ui.AnalysisAndHistoryScreen

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneyManager.moneylens.DataClass.AccountTotal
import com.moneyManager.moneylens.DataClass.CategoryExpense
import com.moneyManager.moneylens.DataClass.HistoryUiState
import com.moneyManager.moneylens.DataClass.TimePeriod
import com.moneyManager.moneylens.database.Entity.Account
import com.moneyManager.moneylens.database.Entity.Category
import com.moneyManager.moneylens.database.Entity.Transaction
import com.moneyManager.moneylens.ui.SaveScreen.repo.SaveScreenRepository
import com.moneyManager.moneylens.ui.theme.BankTint
import com.moneyManager.moneylens.ui.theme.CashTint
import com.moneyManager.moneylens.ui.theme.DefaultAccountTint
import com.moneyManager.moneylens.ui.theme.SavingsTint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

@HiltViewModel
class HistoryViewModal @Inject constructor(
    private val historyRepository: HistoryRepository,
    private val saveScreenRepository: SaveScreenRepository
) : ViewModel() {
    private val _historyUiState = MutableStateFlow(HistoryUiState())
    val historyUiState = _historyUiState.asStateFlow()

    var categories = emptyList<Category>()
    var accounts = emptyList<Account>()

    init {
        viewModelScope.launch {
            categories = historyRepository.getAllCategories().first()
            accounts = historyRepository.getAllAccounts().first()
            observeTransactions()
        }
    }

    fun getCategoryIcon(icon: String): ImageVector {
        return saveScreenRepository.getCategoryIcon(icon)
    }
    fun onPreviousPeriod() {
        _historyUiState.update { state ->
            state.copy(
                selectedDate = when (state.timePeriod) {
                    TimePeriod.WEEK ->
                        state.selectedDate.minusWeeks(1)

                    TimePeriod.MONTH ->
                        state.selectedDate.minusMonths(1)

                    TimePeriod.YEAR ->
                        state.selectedDate.minusYears(1)
                }
            )
        }
        updateDateRange()
    }

    fun onNextPeriod() {
        _historyUiState.update { state ->
            state.copy(
                selectedDate = when (state.timePeriod) {
                    TimePeriod.WEEK ->
                        state.selectedDate.plusWeeks(1)

                    TimePeriod.MONTH ->
                        state.selectedDate.plusMonths(1)

                    TimePeriod.YEAR ->
                        state.selectedDate.plusYears(1)
                }
            )
        }
        updateDateRange()
    }

    private fun getDateRange(
        period: TimePeriod,
        date: LocalDate
    ): Pair<Long, Long> {

        val start: LocalDate
        val end: LocalDate

        when (period) {
            TimePeriod.WEEK -> {
                start = date.with(
                    TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)
                )
                end = start.plusDays(6)
            }

            TimePeriod.MONTH -> {
                start = date.withDayOfMonth(1)
                end = date.withDayOfMonth(date.lengthOfMonth())
            }

            TimePeriod.YEAR -> {
                start = date.withDayOfYear(1)
                end = date.withDayOfYear(date.lengthOfYear())
            }
        }

        val zone = ZoneId.systemDefault()

        val startMillis = start.atStartOfDay(zone)
            .toInstant().toEpochMilli() // need to query the db because timeStamp is saved

        val endMillis = end.plusDays(1)
            .atStartOfDay(zone)
            .toInstant().toEpochMilli() - 1

        return Pair(startMillis, endMillis)
    }

    private fun updateDateRange() {
        val state = _historyUiState.value

        val date = state.selectedDate
        val start: LocalDate
        val end: LocalDate

        when (state.timePeriod) {
            TimePeriod.WEEK -> {
                start = date.with(
                    TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)
                )
                end = start.plusDays(6)
            }

            TimePeriod.MONTH -> {
                start = date.withDayOfMonth(1)
                end = date.withDayOfMonth(date.lengthOfMonth())
            }

            TimePeriod.YEAR -> {
                start = date.withDayOfYear(1)
                end = date.withDayOfYear(date.lengthOfYear())
            }
        }

        val zone = ZoneId.systemDefault()

        val startMillis = start.atStartOfDay(zone)
            .toInstant().toEpochMilli()

        val endMillis = end.plusDays(1)
            .atStartOfDay(zone)
            .toInstant().toEpochMilli() - 1

        _historyUiState.update { currentState ->
            currentState.copy(
                startDate = startMillis,
                endDate = endMillis
            )
        }
    }

    fun onTimePeriodSelected(period: TimePeriod) {
        _historyUiState.update {
            it.copy(timePeriod = period)
        }
        updateDateRange()
    }


    private suspend  fun observeTransactions() {
            combine(
                historyUiState.map { it.timePeriod }.distinctUntilChanged(),
                historyUiState.map { it.selectedDate }.distinctUntilChanged()
            ) { period, date ->
                getDateRange(period, date)
            }
                .distinctUntilChanged()
                .flatMapLatest { (startDate, endDate) ->
                    historyRepository.getTransactionsByDateRange(
                        startDate = startDate,
                        endDate = endDate
                    )
                }
                .collect { transactionList ->
                    _historyUiState.update { currentState ->
                        currentState.copy(
                            transactions = transactionList
                        )
                    }
                    calculateTotals(transactionList)
                    updateCategoryExpenses(transactionList)
                    updateCategoryIncomes(transactionList)
                    updateAccountTotals(transactionList)
                    updateStatistics(transactionList)
                }


    }

    private fun calculateTotals(
        transactions: List<Transaction>
    ) {
        val totalIncome = transactions
            .filter { it.type == "INCOME" }
            .sumOf { it.amount }

        val totalExpenses = transactions
            .filter { it.type == "EXPENSE" }
            .sumOf { it.amount }

        _historyUiState.update { currentState ->
            currentState.copy(
                totalIncome = totalIncome,
                totalExpenses = totalExpenses
            )
        }
    }

    private fun calculateCategoryExpenses(
        transactions: List<Transaction>,
        categories: List<Category>
    ): List<CategoryExpense> {

        val expenseTransactions = transactions.filter { transaction ->
            transaction.type == "EXPENSE"
        }

        val groupedTransactions = expenseTransactions.groupBy { transaction ->
            transaction.categoryId
        }

        val categoryExpenses = mutableListOf<CategoryExpense>()

        for ((categoryId, categoryTransactions) in groupedTransactions) {

            val category = categories.find { category ->
                category.id == categoryId
            }
            if (category == null) {
                continue
            }
            val totalAmount = categoryTransactions.sumOf { transaction ->
                transaction.amount
            }

            val categoryExpense = CategoryExpense(
                categoryId = category.id,
                categoryName = category.name,
                icon = category.icon,
                color = category.color,
                totalAmount = totalAmount
            )

            categoryExpenses.add(categoryExpense)
        }

        categoryExpenses.sortByDescending { categoryExpense ->
            categoryExpense.totalAmount
        }

        return categoryExpenses
    }

    private fun calculateCategoryIncomes(
        transactions: List<Transaction>,
        categories: List<Category>
    ): List<CategoryExpense> {

        val expenseTransactions = transactions.filter { transaction ->
            transaction.type == "INCOME"
        }

        val groupedTransactions = expenseTransactions.groupBy { transaction ->
            transaction.categoryId
        }

        val categoryExpenses = mutableListOf<CategoryExpense>()

        for ((categoryId, categoryTransactions) in groupedTransactions) {

            val category = categories.find { category ->
                category.id == categoryId
            }
            if (category == null) {
                continue
            }
            val totalAmount = categoryTransactions.sumOf { transaction ->
                transaction.amount
            }

            val categoryExpense = CategoryExpense(
                categoryId = category.id,
                categoryName = category.name,
                icon = category.icon,
                color = category.color,
                totalAmount = totalAmount
            )

            categoryExpenses.add(categoryExpense)
        }

        categoryExpenses.sortByDescending { categoryExpense ->
            categoryExpense.totalAmount
        }

        return categoryExpenses
    }

    private fun updateCategoryExpenses(
        transactions: List<Transaction>
    ) {
        val categoryExpenses = calculateCategoryExpenses(
            transactions = transactions,
            categories = categories
        )

        _historyUiState.update { currentState ->
            currentState.copy(
                categoryExpenses = categoryExpenses
            )
        }
    }

    private fun updateCategoryIncomes(transactionList: List<Transaction>) {

        val categoryIncome = calculateCategoryIncomes(transactionList,categories)
        _historyUiState.update { currentState ->
            currentState.copy(
                categoryIncomes = categoryIncome
            )
        }
    }

    private fun updateAccountTotals(
        transactions: List<Transaction>
    ) {
        val expenseTotals = calculateAccountTotals(
            transactions.filter { it.type == "EXPENSE" }
        )

        val incomeTotals = calculateAccountTotals(
            transactions.filter { it.type == "INCOME" }
        )

        _historyUiState.update { currentState ->
            currentState.copy(
                accountExpenses = expenseTotals,
                accountIncomes = incomeTotals
            )
        }
    }

    private fun calculateAccountTotals(
        transactions: List<Transaction>
    ): List<AccountTotal> {

        val groupedTransactions = transactions.groupBy {
            it.accountId
        }

        val accountTotals = mutableListOf<AccountTotal>()

        for ((accountId, accountTransactions) in groupedTransactions) {
            val account = accounts.find {
                it.id == accountId
            } ?: continue

            val totalAmount = accountTransactions.sumOf {
                it.amount
            }

            accountTotals.add(
                AccountTotal(
                    accountId = account.id,
                    accountIcon = account.icon,
                    accountName = account.name,
                    totalAmount = totalAmount
                )
            )
        }

        return accountTotals.sortedByDescending {
            it.totalAmount
        }
    }

    fun getAccountIcon(icon: String): ImageVector {
        return saveScreenRepository.getAccountIcon(icon)
    }
    fun getAccountTint(accountName: String): Color {
        return when (accountName.lowercase()) {
            "cash" -> CashTint
            "bank" -> BankTint
            "savings" -> SavingsTint
            else -> DefaultAccountTint
        }
    }

    private fun updateStatistics(
        transactionList: List<Transaction>
    ) {
        val income = transactionList
            .filter { it.type == "INCOME" }
            .sumOf { it.amount }

        val expenses = transactionList
            .filter { it.type == "EXPENSE" }
            .sumOf { it.amount }

        val highestExpense = transactionList
            .filter { it.type == "EXPENSE" }
            .maxOfOrNull { it.amount } ?: 0.0

        _historyUiState.update { currentState ->
            currentState.copy(
                netSavings = income - expenses,
                transactionCount = transactionList.size,
                highestExpense = highestExpense
            )
        }
    }
}
