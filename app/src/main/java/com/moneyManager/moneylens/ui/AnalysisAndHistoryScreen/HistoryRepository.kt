package com.moneyManager.moneylens.ui.AnalysisAndHistoryScreen

import com.moneyManager.moneylens.database.Entity.Account
import com.moneyManager.moneylens.database.Entity.Category
import com.moneyManager.moneylens.database.Entity.Dao.AccountDao
import com.moneyManager.moneylens.database.Entity.Dao.CategoryDao
import com.moneyManager.moneylens.database.Entity.Dao.TransactionDao
import com.moneyManager.moneylens.database.Entity.Transaction
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class HistoryRepository @Inject constructor( private val transactionDao: TransactionDao,
    private val categoryDao: CategoryDao,
    private val accountDao: AccountDao) {

    fun getTransactionsByDateRange(
        startDate: Long,
        endDate: Long
    ): Flow<List<Transaction>> {
        return transactionDao.getByDateRange(
            startDate = startDate,
            endDate = endDate
        )
    }

    fun getAllCategories(): Flow<List<Category>> {
        return categoryDao.getAll()
    }
    fun getAllAccounts(): Flow<List<Account>> {
        return accountDao.getAll()
    }
}