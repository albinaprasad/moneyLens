package com.moneyManager.moneylens.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.moneyManager.moneylens.enums.AppLaunchState
import com.moneyManager.moneylens.preferences.DataStoreKeys.LAUNCH_STATE_KEY
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Singleton

@Singleton
class UserPreference @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    val currentAppState: Flow<AppLaunchState> = dataStore.data.map { preferences ->
        val stateString = preferences[LAUNCH_STATE_KEY]
        try {
            if (stateString != null) AppLaunchState.valueOf(stateString) else AppLaunchState.WALKTHROUGH
        } catch (e: Exception) {
            AppLaunchState.WALKTHROUGH
        }
    }

    suspend fun setLaunchState(state: AppLaunchState) {
        dataStore.edit { preferences ->
            preferences[LAUNCH_STATE_KEY] = state.name
        }
    }
}
