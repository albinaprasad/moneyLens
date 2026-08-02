package com.moneyManager.moneylens.database.Entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "budgets",
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("categoryId")]
)
data class Budget(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val limitAmount: Double,
    val month: Int,          // 1-12
    val year: Int,           // 2026
    val categoryId: Int
)
