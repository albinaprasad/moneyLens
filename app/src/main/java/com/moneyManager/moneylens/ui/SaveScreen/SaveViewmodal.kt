package com.moneyManager.moneylens.ui.SaveScreen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class SaveViewmodal @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(AddTransactionState())
    val uiState: StateFlow<AddTransactionState> = _uiState.asStateFlow()

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