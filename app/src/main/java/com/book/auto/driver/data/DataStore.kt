package com.book.auto.driver.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStore(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DataStorePref.DATASTORE_NAME)
        private val PREF_KEY_LOGIN = booleanPreferencesKey(DataStorePref.PREF_LOGIN)
        private val PREF_KEY_EMAIL = stringPreferencesKey(DataStorePref.PREF_EMAIL)
        private val PREF_KEY_NAME = stringPreferencesKey(DataStorePref.PREF_NAME)
    }


    val isLogin: Flow<Boolean>
        get() = context.dataStore.data
            .map {
                it[PREF_KEY_LOGIN] ?: false
            }


    suspend fun setLogin(b: Boolean) {
        context.dataStore.edit { it[PREF_KEY_LOGIN] = b }
    }

    val getEmail: Flow<String>
        get() = context.dataStore.data
            .map {
                it[PREF_KEY_EMAIL] ?: ""
            }


    suspend fun setEmail(value: String) {
        context.dataStore.edit { it[PREF_KEY_EMAIL] = value }
    }

    val getName: Flow<String>
        get() = context.dataStore.data
            .map {
                it[PREF_KEY_NAME] ?: ""
            }


    suspend fun setName(value: String) {
        context.dataStore.edit { it[PREF_KEY_NAME] = value }
    }

}

class DataStorePref {
    companion object {
        const val DATASTORE_NAME = "credentials"
        const val PREF_LOGIN = "pref_login"
        const val PREF_EMAIL = "pref_email"
        const val PREF_NAME = "pref_name"
    }
}