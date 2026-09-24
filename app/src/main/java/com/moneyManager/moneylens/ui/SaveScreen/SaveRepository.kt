package com.moneyManager.moneylens.ui.SaveScreen

class SaveRepository {
    fun getCategories(): List<String> {
        return listOf(
            "Food",
            "Transport",
            "Shopping",
            "Bills",
            "Entertainment",
            "Health",
            "Education",
            "Others"
        )
    }

    fun getPaymentModes(): List<String> {
        return listOf(
            "Cash",
            "UPI",
            "Credit Card",
            "Debit Card",
            "Bank Transfer"
        )
    }
}