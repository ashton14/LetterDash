package com.zybooks.letterdash

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


data class AppPreferences(

    val soundEnabled: Boolean = true,
    val difficulty: Difficulty = Difficulty.NORMAL,
    val highScore: Int = 0

)

class AppStorage(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences>
            by preferencesDataStore("app_storage")

        private object PreferencesKeys {
            val SOUND_ENABLED = booleanPreferencesKey("sound_enabled")
            val DIFFICULTY = stringPreferencesKey("difficulty")
            val HIGH_SCORE = intPreferencesKey("high_score")
        }
    }

    val appPreferencesFlow: Flow<AppPreferences> =
        context.dataStore.data.map { preferences ->
            val soundEnabled = preferences[PreferencesKeys.SOUND_ENABLED] ?: true
            val difficulty = preferences[PreferencesKeys.DIFFICULTY] ?: Difficulty.NORMAL.name
            val highScore = preferences[PreferencesKeys.HIGH_SCORE] ?: 0

            AppPreferences(soundEnabled, Difficulty.valueOf(difficulty), highScore)

        }

    suspend fun updateSoundEnabled(soundEnabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.SOUND_ENABLED] = soundEnabled
        }
    }

    suspend fun updateDifficulty(difficulty: Difficulty) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.DIFFICULTY] = difficulty.name
        }
    }

    suspend fun updateHighScore(highScore: Int) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.HIGH_SCORE] = highScore
        }
    }

}