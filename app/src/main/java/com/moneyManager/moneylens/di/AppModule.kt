package com.moneyManager.moneylens.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.moneyManager.moneylens.database.Entity.Dao.AccountDao
import com.moneyManager.moneylens.database.Entity.Dao.BudgetDao
import com.moneyManager.moneylens.database.Entity.Dao.CategoryDao
import com.moneyManager.moneylens.database.Entity.Dao.TransactionDao
import com.moneyManager.moneylens.database.Entity.MoneyLensDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    // ── Room Database ──────────────────────────────────────────────

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MoneyLensDatabase {
        return Room.databaseBuilder(
            context,
            MoneyLensDatabase::class.java,
            "money_lens_db"
        ).build()
    }

    // ── DAOs ────────────────────────────────────────────────────

    @Provides
    fun provideAccountDao(database: MoneyLensDatabase): AccountDao {
        return database.accountDao()
    }

    @Provides
    fun provideCategoryDao(database: MoneyLensDatabase): CategoryDao {
        return database.categoryDao()
    }

    @Provides
    fun provideTransactionDao(database: MoneyLensDatabase): TransactionDao {
        return database.transactionDao()
    }

    @Provides
    fun provideBudgetDao(database: MoneyLensDatabase): BudgetDao {
        return database.budgetDao()
    }
}
