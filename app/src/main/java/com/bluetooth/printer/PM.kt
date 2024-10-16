package com.bluetooth.printer

import android.content.SharedPreferences

class PM(private val preferences: SharedPreferences) {
    fun clearAll() {
        preferences.edit().putBoolean(IS_LOGGED_IN, false).apply()
    }

    var isLoggedIn: Boolean
        get() = preferences.getBoolean(IS_LOGGED_IN, false)
        set(value) {
            preferences.edit().putBoolean(IS_LOGGED_IN, value).apply()
        }

    var btDeviceAddress: String
        get() = preferences.getString(BT_DEVICE_ADDRESS, "").toString()
        set(value) {
            preferences.edit().putString(BT_DEVICE_ADDRESS, value).apply()
        }

    var btDeviceName: String
        get() = preferences.getString(BT_DEVICE_NAME, "").toString()
        set(value) {
            preferences.edit().putString(BT_DEVICE_NAME, value).apply()
        }

    var btDeviceAddress2: String
        get() = preferences.getString(BT_DEVICE_ADDRESS2, "").toString()
        set(value) {
            preferences.edit().putString(BT_DEVICE_ADDRESS2, value).apply()
        }

    var btDeviceName2: String
        get() = preferences.getString(BT_DEVICE_NAME2, "").toString()
        set(value) {
            preferences.edit().putString(BT_DEVICE_NAME2, value).apply()
        }

    companion object {
        const val PM_FILE = "pm_file"
        private const val IS_LOGGED_IN = "is_logged_in"
        private const val BT_DEVICE_ADDRESS = "bt_device_address"
        private const val BT_DEVICE_ADDRESS2 = "bt_device_address2"
        private const val BT_DEVICE_NAME = "bt_device_name"
        private const val BT_DEVICE_NAME2 = "bt_device_name2"
    }
}
