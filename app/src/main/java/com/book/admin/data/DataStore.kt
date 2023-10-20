package com.book.admin.data

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
        private val PREF_KEY_PASSWORD = stringPreferencesKey(DataStorePref.PREF_PASSWORD)
    }


    val isLogin: Flow<Boolean>
        get() = context.dataStore.data
            .map {
                it[PREF_KEY_LOGIN] ?: false
            }


    suspend fun setLogin(b: Boolean, pass: String, callback: () -> Unit) {
        context.dataStore.edit {
            it[PREF_KEY_LOGIN] = b
            it[PREF_KEY_PASSWORD] = pass
        }

        callback()
    }

    val getPassword: Flow<String>
        get() = context.dataStore.data
            .map {
                it[PREF_KEY_PASSWORD] ?: ""
            }


}

class DataStorePref {
    companion object {
        const val DATASTORE_NAME = "credentials"
        const val PREF_LOGIN = "pref_login"
        const val PREF_PASSWORD = "pref_password"
    }
}