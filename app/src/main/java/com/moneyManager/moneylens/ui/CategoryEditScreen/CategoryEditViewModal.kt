package com.moneyManager.moneylens.ui.CategoryEditScreen

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneyManager.moneylens.database.Entity.Category
import com.moneyManager.moneylens.database.Entity.Dao.CategoryDao
import com.moneyManager.moneylens.ui.SaveScreen.repo.SaveScreenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CategoryEditViewModal @Inject constructor(
    val categoryDao: CategoryDao,
    val saveScreenRepository: SaveScreenRepository
) : ViewModel() {

    val expenseCategories: StateFlow<List<Category>> =
        categoryDao.getByType("EXPENSE")
            .flowOn(Dispatchers.IO)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    val incomeCategories: StateFlow<List<Category>> =
        categoryDao.getByType("INCOME")
            .flowOn(Dispatchers.IO)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun insertCategory(category: Category){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                categoryDao.insert(category)
            }
        }
    }
    fun updateCategory(category: Category) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                categoryDao.update(category)
            }
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                categoryDao.delete(category)
            }
        }
    }

    fun getCategoryIcon(icon: String): ImageVector {
        return saveScreenRepository.getCategoryIcon(icon)
    }
}