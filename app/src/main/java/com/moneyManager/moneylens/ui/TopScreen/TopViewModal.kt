package com.moneyManager.moneylens.ui.TopScreen

import androidx.lifecycle.ViewModel
import com.moneyManager.moneylens.DataClass.BottomNavigationItems
import com.moneyManager.moneylens.ui.Bottombar.BottomNavigationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class TopViewModal @Inject constructor(
    bottomNavigationRepository: BottomNavigationRepository
):ViewModel() {
    private val _bottomNavItems = MutableStateFlow<List<BottomNavigationItems>>(emptyList())
    val bottomNavItems: StateFlow<List<BottomNavigationItems>> = _bottomNavItems

    init {
        _bottomNavItems.value  = bottomNavigationRepository.getBottomNavItems()
    }
}