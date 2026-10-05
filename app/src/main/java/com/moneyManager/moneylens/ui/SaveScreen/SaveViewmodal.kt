package com.moneyManager.moneylens.ui.SaveScreen

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneyManager.moneylens.database.Entity.Account
import com.moneyManager.moneylens.database.Entity.Category
import com.moneyManager.moneylens.ui.SaveScreen.repo.SaveScreenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject


data class BottomSheetUiState(
    val categories: List<Category> = emptyList(),
    val accounts: List<Account> = emptyList(),

    val showCategorySheet: Boolean = false,
    val showAccountSheet: Boolean = false
)

@HiltViewModel
class SaveViewmodal @Inject constructor(
    private val saveRepo: SaveScreenRepository
) : ViewModel() {

    private var _uiState = MutableStateFlow(AddTransactionState())
    val uiState: StateFlow<AddTransactionState> = _uiState.asStateFlow()

    private val _bottomSheetUiState = MutableStateFlow(BottomSheetUiState())
    val bottomSheetUiState = _bottomSheetUiState.asStateFlow()

    init {
        viewModelScope.launch {
            saveRepo.initializeCategories()
            saveRepo.initializeAccounts()
        }
        viewModelScope.launch {
            getCategoriesToShow()
        }
        viewModelScope.launch {
            getAccountsToShow()
        }
    }

    fun openCategorySheet() {
        _bottomSheetUiState.update {
            it.copy(showCategorySheet = true)
        }
    }
    fun openAccountSheet() {
        _bottomSheetUiState.update {
            it.copy(showAccountSheet = true)
        }
    }

    fun closeCategorySheet() {
        _bottomSheetUiState.update {
            it.copy(showCategorySheet = false)
        }
    }

    fun closeAccountSheet() {
        _bottomSheetUiState.update {
            it.copy(showAccountSheet = false)
        }
    }
    suspend  fun getCategoriesToShow(){
        saveRepo.getCategories().collect { categories ->
            _bottomSheetUiState.update {
                it.copy(categories = categories)
            }
        }
    }

    suspend fun getAccountsToShow(){
        saveRepo.getAccounts().collect { accounts ->
            _bottomSheetUiState.update {
                it.copy(accounts =accounts)
            }
        }
    }
    fun getCategoryIcon(icon: String): ImageVector {
       return saveRepo.getCategoryIcon(icon)
    }
    fun getAccountIcon(icon: String): ImageVector {
       return saveRepo.getAccountIcon(icon)
    }
    fun onTypeSelected(type: TransactionType) {
        _uiState.update { it.copy(type = type) }
    }

    fun onAmountChange(value: String) {
        // Allow only a plain, single-decimal numeric string.
        if (value.isEmpty() || value.matches(Regex("^\\d*\\.?\\d*$"))) {
            _uiState.update { it.copy(amount = value) }
        }
    }

    fun onCategorySelected(category: String) {
        _uiState.update { it.copy(category = category) }
    }

    fun onPaymentModeSelected(mode: String) {
        _uiState.update { it.copy(paymentMode = mode) }
    }

    fun onNoteChange(note: String) {
        _uiState.update { it.copy(note = note) }
    }

    fun onDateSelected(date: LocalDate) {
        _uiState.update { it.copy(date = date) }
    }

    fun onTimeSelected(time: LocalTime) {
        _uiState.update { it.copy(time = time) }
    }


}