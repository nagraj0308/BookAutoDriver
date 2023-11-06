package com.book.gaadi

import android.content.SharedPreferences

class PM(private val preferences: SharedPreferences) {
    fun clearAll() {
        preferences.edit().clear().apply()
    }

    var isLoggedIn: Boolean
        get() = preferences.getBoolean(IS_LOGGED_IN, false)
        set(value) {
            preferences.edit().putBoolean(IS_LOGGED_IN, value).apply()
        }
    var gmail: String?
        get() = preferences.getString(GMAIL, "")
        set(gmail) {
            preferences.edit().putString(GMAIL, gmail).apply()
        }


    var name: String?
        get() = preferences.getString(NAME, "")
        set(name) {
            preferences.edit().putString(NAME, name).apply()
        }


    companion object {
        const val PM_FILE = "pm_file"
        private const val IS_LOGGED_IN = "is_logged_in"
        private const val GMAIL = "gmail"
        private const val NAME = "name"
    }
}
