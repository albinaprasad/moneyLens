package com.moneyManager.moneylens.preferences

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

private val Context.dataStore by preferencesDataStore("app_preferences")

class UserPreference(private val context: Context){

}
