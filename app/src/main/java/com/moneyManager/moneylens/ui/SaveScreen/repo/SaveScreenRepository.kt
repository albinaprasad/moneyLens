package com.moneyManager.moneylens.ui.SaveScreen.repo

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.automirrored.filled.ShowChart
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import com.moneyManager.moneylens.database.Entity.Category
import com.moneyManager.moneylens.database.Entity.Dao.CategoryDao
import com.moneyManager.moneylens.ui.theme.CategoryBills
import com.moneyManager.moneylens.ui.theme.CategoryEducation
import com.moneyManager.moneylens.ui.theme.CategoryEntertainment
import com.moneyManager.moneylens.ui.theme.CategoryFood
import com.moneyManager.moneylens.ui.theme.CategoryGifts
import com.moneyManager.moneylens.ui.theme.CategoryInsurance
import com.moneyManager.moneylens.ui.theme.CategoryInvestments
import com.moneyManager.moneylens.ui.theme.CategoryMedical
import com.moneyManager.moneylens.ui.theme.CategoryOthers
import com.moneyManager.moneylens.ui.theme.CategoryPersonalCare
import com.moneyManager.moneylens.ui.theme.CategoryRent
import com.moneyManager.moneylens.ui.theme.CategoryShopping
import com.moneyManager.moneylens.ui.theme.CategoryTaxes
import com.moneyManager.moneylens.ui.theme.CategoryTravel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class SaveScreenRepository@Inject constructor( private val categoryDao: CategoryDao) {

    fun  getDefaultCategories() = listOf(
        Category(
            name = "Others",
            icon = "MoreHoriz",
            type = "EXPENSE",
            color = CategoryOthers.toArgb(),
            isDefault = true
        ),
        Category(
            name = "Food and Dining",
            icon = "Restaurant",
            type = "EXPENSE",
            color = CategoryFood.toArgb(),
            isDefault = true
        ),
        Category(
            name = "Shopping",
            icon = "ShoppingCart",
            type = "EXPENSE",
            color = CategoryShopping.toArgb(),
            isDefault = true
        ),
        Category(
            name = "Travelling",
            icon = "Flight",
            type = "EXPENSE",
            color = CategoryTravel.toArgb(),
            isDefault = true
        ),
        Category(
            name = "Entertainment",
            icon = "SportsEsports",
            type = "EXPENSE",
            color = CategoryEntertainment.toArgb(),
            isDefault = true
        ),
        Category(
            name = "Medical",
            icon = "MedicalServices",
            type = "EXPENSE",
            color = CategoryMedical.toArgb(),
            isDefault = true
        ),
        Category(
            name = "Personal Care",
            icon = "Spa",
            type = "EXPENSE",
            color = CategoryPersonalCare.toArgb(),
            isDefault = true
        ),
        Category(
            name = "Education",
            icon = "School",
            type = "EXPENSE",
            color = CategoryEducation.toArgb(),
            isDefault = true
        ),
        Category(
            name = "Bills and Utilities",
            icon = "ReceiptLong",
            type = "EXPENSE",
            color = CategoryBills.toArgb(),
            isDefault = true
        ),
        Category(
            name = "Investments",
            icon = "ShowChart",
            type = "EXPENSE",
            color = CategoryInvestments.toArgb(),
            isDefault = true
        ),
        Category(
            name = "Rent",
            icon = "Home",
            type = "EXPENSE",
            color = CategoryRent.toArgb(),
            isDefault = true
        ),
        Category(
            name = "Taxes",
            icon = "Receipt",
            type = "EXPENSE",
            color = CategoryTaxes.toArgb(),
            isDefault = true
        ),
        Category(
            name = "Insurance",
            icon = "VerifiedUser",
            type = "EXPENSE",
            color = CategoryInsurance.toArgb(),
            isDefault = true
        ),
        Category(
            name = "Gifts and Donation",
            icon = "CardGiftcard",
            type = "EXPENSE",
            color = CategoryGifts.toArgb(),
            isDefault = true
        )
    )

    fun getCategoryIcon(icon: String): ImageVector {
        return when (icon) {
            "MoreHoriz" -> Icons.Default.MoreHoriz
            "Restaurant" -> Icons.Default.Restaurant
            "ShoppingCart" -> Icons.Default.ShoppingCart
            "Flight" -> Icons.Default.Flight
            "SportsEsports" -> Icons.Default.SportsEsports
            "MedicalServices" -> Icons.Default.MedicalServices
            "Spa" -> Icons.Default.Spa
            "School" -> Icons.Default.School
            "ReceiptLong" -> Icons.AutoMirrored.Filled.ReceiptLong
            "ShowChart" -> Icons.AutoMirrored.Filled.ShowChart
            "Home" -> Icons.Default.Home
            "Receipt" -> Icons.Default.Receipt
            "VerifiedUser" -> Icons.Default.VerifiedUser
            "CardGiftcard" -> Icons.Default.CardGiftcard
            else -> Icons.Default.Category
        }
    }



    suspend fun initializeCategories() {
        if (categoryDao.getCategoryCount() == 0) {
            categoryDao.insertAll(getDefaultCategories())
        }
    }

    fun getCategories(): Flow<List<Category>> {
        return categoryDao.getAllCategories()
    }


}