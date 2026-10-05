package com.moneyManager.moneylens.ui.TopScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneyManager.moneylens.DataClass.BottomNavigationItems
import com.moneyManager.moneylens.database.Entity.Dao.TransactionDao
import com.moneyManager.moneylens.ui.Bottombar.BottomNavigationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class TopViewModal @Inject constructor(
    bottomNavigationRepository: BottomNavigationRepository,
    transactionDao: TransactionDao
) : ViewModel() {
    private val _bottomNavItems = MutableStateFlow<List<BottomNavigationItems>>(emptyList())
    val bottomNavItems: StateFlow<List<BottomNavigationItems>> = _bottomNavItems

    init {
        _bottomNavItems.value = bottomNavigationRepository.getBottomNavItems()
    }

    // Current month boundaries as epoch millis
    private val now = LocalDate.now()
    private val startOfMonth = now.withDayOfMonth(1)
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant().toEpochMilli()
    private val endOfMonth = now.withDayOfMonth(now.lengthOfMonth())
        .atTime(23, 59, 59)
        .atZone(ZoneId.systemDefault())
        .toInstant().toEpochMilli()

    val totalIncome: StateFlow<Double> = transactionDao
        .getTotalByTypeAndDateRange("INCOME", startOfMonth, endOfMonth)
        .map { it ?: 0.0 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val totalExpenses: StateFlow<Double> = transactionDao
        .getTotalByTypeAndDateRange("EXPENSE", startOfMonth, endOfMonth)
        .map { it ?: 0.0 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val totalBalance: StateFlow<Double> = combine(totalIncome, totalExpenses) { income, expense ->
        income - expense
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)
}