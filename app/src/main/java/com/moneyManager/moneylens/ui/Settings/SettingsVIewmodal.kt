package com.moneyManager.moneylens.ui.Settings

import androidx.lifecycle.ViewModel
import com.moneyManager.moneylens.DataClass.SettingsCardOptionItem
import com.moneyManager.moneylens.ui.Settings.repo.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class SettingsViewmodal @Inject constructor(private val repo: SettingsRepository) : ViewModel() {

    private val _settingsMenuCards = MutableStateFlow<List<SettingsCardOptionItem>>(emptyList())
    val settingsMenuCards: StateFlow<List<SettingsCardOptionItem>> = _settingsMenuCards.asStateFlow()

    init {
        loadOptions()
    }

    private fun loadOptions() {
        _settingsMenuCards.value = repo.getSettingsCardOptions()
    }
}