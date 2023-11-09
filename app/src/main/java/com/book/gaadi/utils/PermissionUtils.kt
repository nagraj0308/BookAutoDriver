package com.book.gaadi.utils


import android.Manifest.permission
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse


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


        fun checkLocationAccessPermission(activity: Activity): Boolean {
            return activity.checkSelfPermission(permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || activity.checkSelfPermission(
                permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
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

    }
}

