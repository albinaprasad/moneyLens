package com.moneyManager.moneylens.ui.Settings.repo

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Campaign
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.StarOutline
import com.moneyManager.moneylens.DataClass.SettingsCardOptionItem
import com.moneyManager.moneylens.navigation.AppScreens
import com.moneyManager.moneylens.navigation.AppScreens.InviteRoute
import com.moneyManager.moneylens.navigation.AppScreens.SettingsScreen
import javax.inject.Inject

class SettingsRepository @Inject constructor() {

    fun getSettingsCardOptions():List<SettingsCardOptionItem>{
          return listOf(
              SettingsCardOptionItem("settings", Icons.Outlined.Settings, SettingsScreen),
              SettingsCardOptionItem("invite", Icons.Outlined.Campaign, InviteRoute),
              SettingsCardOptionItem("rate", Icons.Outlined.StarOutline, AppScreens.RateRoute ),
              SettingsCardOptionItem("feedback", Icons.Outlined.ChatBubbleOutline, AppScreens.FeedbackRoute),
              SettingsCardOptionItem("faq", Icons.AutoMirrored.Outlined.HelpOutline, AppScreens.FaqRoute),
              SettingsCardOptionItem("about", Icons.Outlined.Info, AppScreens.AboutRoute)
          )
    }

}