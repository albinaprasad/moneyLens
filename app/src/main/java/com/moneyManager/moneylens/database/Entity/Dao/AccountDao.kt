package com.moneyManager.moneylens.database.Entity.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.moneyManager.moneylens.database.Entity.Account
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(account: Account): Long

    @Update
    suspend fun update(account: Account)

    @Delete
    suspend fun delete(account: Account)

    @Query("SELECT * FROM accounts ORDER BY name ASC")
    fun getAll(): Flow<List<Account>>

    @Query("SELECT * FROM accounts WHERE id = :id")
    fun getById(id: Int): Flow<Account?>

    @Query("UPDATE accounts SET balance = :newBalance WHERE id = :id")
    suspend fun updateBalance(id: Int, newBalance: Double)
}
