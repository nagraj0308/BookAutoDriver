package com.bluetooth.printer

import android.content.SharedPreferences
import com.rent.house.utils.Language

class PM(private val preferences: SharedPreferences) {
    fun clearAll() {
        preferences.edit().putBoolean(IS_LOGGED_IN, false).apply()
        preferences.edit().putString(GMAIL, "").apply()
        preferences.edit().putString(NAME, "").apply()
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

    var lang: String?
        get() = preferences.getString(LANG, Language.getLanguageCode(Language.DEFAULT))
        set(lang) {
            preferences.edit().putString(LANG, lang).apply()
        }

    var langSet: Boolean
        get() = preferences.getBoolean(LANG_SET, false)
        set(value) {
            preferences.edit().putBoolean(LANG_SET, value).apply()
        }


    companion object {
        const val PM_FILE = "pm_file"
        private const val IS_LOGGED_IN = "is_logged_in"
        private const val GMAIL = "gmail"
        private const val NAME = "name"
        private const val LANG = "lang"
        private const val LANG_SET = "lang_set"
    }
}
