package com.bluetooth.printer.view.utils


import android.Manifest.permission
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat


class RequestCodePermission {
    companion object {
        const val READ_STORAGE = 11
        const val BT_DEVICE_CONNECT = 12

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
                    arrayOf(permission.READ_EXTERNAL_STORAGE), RequestCodePermission.READ_STORAGE
                )
            }
        }

        fun checkBTDeviceConnectPermission(context: Context): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val readPermission: Int =
                    ContextCompat.checkSelfPermission(context, permission.BLUETOOTH_CONNECT)
                readPermission == PackageManager.PERMISSION_GRANTED
            } else {
                true
            }
        }

        fun requestBTDeviceConnectPermission(activity: Activity) {
            if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.S) {
                activity.requestPermissions(
                    arrayOf(permission.BLUETOOTH_CONNECT), RequestCodePermission.BT_DEVICE_CONNECT
                )
            }
        }

    }
}

