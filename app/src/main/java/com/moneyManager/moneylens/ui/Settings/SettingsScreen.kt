package com.moneyManager.moneylens.ui.Settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moneyManager.moneylens.DataClass.SettingsCardOptionItem
import com.moneyManager.moneylens.navigation.AppScreens
import com.moneyManager.moneylens.ui.TopScreen.TopBar

@Composable
fun SettingsScreen( onNavigate: (AppScreens) -> Unit, ){
    val viewModel = hiltViewModel<SettingsViewmodal>()

    val settingsList by  viewModel.settingsMenuCards.collectAsStateWithLifecycle()
    SettingScreenPortrait(
        settingsList,
        onOptionClick = { route ->
            onNavigate(route)
        }
    )

}

@Composable
fun SettingScreenPortrait(settingsList: List<SettingsCardOptionItem>,onOptionClick: (AppScreens) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ){
        TopBar()
        SettingsCardComponent(menuItems = settingsList, onOptionClick = onOptionClick)

    }

}

@Preview(showBackground = true)
@Composable
fun previewSettings() {
    TopBar()
}