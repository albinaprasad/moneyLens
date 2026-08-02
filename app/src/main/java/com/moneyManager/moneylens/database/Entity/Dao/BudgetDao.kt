package com.moneyManager.moneylens.database.Entity.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.moneyManager.moneylens.database.Entity.Budget
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(budget: Budget): Long

    @Update
    suspend fun update(budget: Budget)

    @Delete
    suspend fun delete(budget: Budget)

    @Query("SELECT * FROM budgets ORDER BY year DESC, month DESC")
    fun getAll(): Flow<List<Budget>>

    @Query("SELECT * FROM budgets WHERE month = :month AND year = :year")
    fun getByMonthAndYear(month: Int, year: Int): Flow<List<Budget>>

    @Query("SELECT * FROM budgets WHERE categoryId = :categoryId AND month = :month AND year = :year")
    fun getByCategoryAndMonth(categoryId: Int, month: Int, year: Int): Flow<Budget?>
}
