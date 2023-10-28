package com.book.auto.driver.utils


import android.Manifest
import android.Manifest.permission
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.book.auto.driver.presentation.pl.LocationService
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse

class PermissionUtils {


    companion object {
        private const val REQUEST_CODE_READ_STORAGE = 13
        private const val REQUEST_CODE_POST_NOTIFICATION = 14

        fun requestShowNotificationPermission(activity: Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                activity.requestPermissions(
                    arrayOf(permission.POST_NOTIFICATIONS), REQUEST_CODE_POST_NOTIFICATION
                )
            }
        }

        fun checkPostNotificationPermission(context: Context): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val readPermission: Int =
                    ContextCompat.checkSelfPermission(context, permission.POST_NOTIFICATIONS)
                readPermission == PackageManager.PERMISSION_GRANTED
            } else {
                true
            }
        }

        fun requestLocationAccessPermissions(activity: Activity) {
            ActivityCompat.requestPermissions(
                activity, arrayOf(
                    permission.ACCESS_COARSE_LOCATION,
                    permission.ACCESS_FINE_LOCATION,
                ), RequestCode.LOCATION
            )
        }


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
                    arrayOf(permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_READ_STORAGE
                )
            }
        }

        fun requestLocationAccessPermission(activity: Activity) {
            ActivityCompat.requestPermissions(
                activity, arrayOf(
                    permission.ACCESS_FINE_LOCATION,
                ), RequestCode.LOCATION
            )
        }

        fun checkLocationAccessPermission(activity: Activity): Boolean {
            return activity.checkSelfPermission(permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        }

        fun checkLocationEnabled(activity: Activity): Boolean {
            val mLocationManager =
                activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        }


        fun requestLocationEnableRequest(activity: Activity) {
            val locationRequest: LocationRequest = LocationRequest.create()
            val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
            LocationServices.getSettingsClient(activity).checkLocationSettings(builder.build())
                .addOnSuccessListener(
                    activity
                ) { response: LocationSettingsResponse? -> }.addOnFailureListener(
                    activity
                ) { ex: Exception? ->
                    if (ex is ResolvableApiException) {
                        try {
                            ex.startResolutionForResult(
                                activity, 10
                            )
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
        }


        fun start(context: Context) {
            Intent(context, LocationService::class.java).apply {
                action = LocationService.ACTION_START
                context.startService(this)
            }
        }

        fun stop(context: Context) {
            Intent(context, LocationService::class.java).apply {
                action = LocationService.ACTION_STOP
                context.startService(this)
            }
        }

    }
}

class RequestCode {
    companion object {
        const val LOCATION = 10
    }
}