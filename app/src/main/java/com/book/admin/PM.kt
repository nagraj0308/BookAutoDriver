package com.book.admin


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
    var password: String?
        get() = preferences.getString(PASSWORD, "")
        set(gmail) {
            preferences.edit().putString(PASSWORD, gmail).apply()
        }


    companion object {
        const val PM_FILE = "pm_file"
        private const val IS_LOGGED_IN = "is_logged_in"
        private const val PASSWORD = "password"
    }
}
