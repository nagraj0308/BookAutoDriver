package com.bluetooth.printer.view.utils


import android.Manifest.permission
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat


class RequestCode {
    companion object {
        const val LOCATION = 10
        const val READ_STORAGE = 11
    }
}

class PermissionUtils {

    companion object {

        fun checkReadStoragePermission(context: Context): Boolean {
            return if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
                val readPermission: Int =
                    ContextCompat.checkSelfPermission(context, permission.READ_EXTERNAL_STORAGE)
                readPermission == PackageManager.PERMISSION_GRANTED
            } else {
                true
            }
        }

        fun requestReadStoragePermission(activity: Activity) {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
                activity.requestPermissions(
                    arrayOf(permission.READ_EXTERNAL_STORAGE), RequestCode.READ_STORAGE
                )
            }
        }

    }
}

