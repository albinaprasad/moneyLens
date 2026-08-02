package com.moneyManager.moneylens.database.Entity

import androidx.room.Database
import androidx.room.RoomDatabase
import com.moneyManager.moneylens.database.Entity.Dao.AccountDao
import com.moneyManager.moneylens.database.Entity.Dao.BudgetDao
import com.moneyManager.moneylens.database.Entity.Dao.CategoryDao
import com.moneyManager.moneylens.database.Entity.Dao.TransactionDao

@Database(
    entities = [Account::class, Category::class, Transaction::class, Budget::class],
    version = 1,
    exportSchema = false
)
abstract class MoneyLensDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun categoryDao(): CategoryDao
    abstract fun transactionDao(): TransactionDao
    abstract fun budgetDao(): BudgetDao
}
