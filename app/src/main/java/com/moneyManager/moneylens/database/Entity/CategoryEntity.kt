package com.moneyManager.moneylens.database.Entity
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,      // "Food", "Transport", "Salary"
    val icon: String,      // icon identifier
    val type: String,      // "INCOME" or "EXPENSE"
    val color: Int         // Color as Int (e.g. Color.toArgb())
)
