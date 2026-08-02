package com.moneyManager.moneylens.database.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,          // "Cash", "Bank", "Savings"
    val balance: Double = 0.0,
    val icon: String = "wallet" // icon identifier
)