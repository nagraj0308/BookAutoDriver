package com.book.auto.utils


import android.Manifest.permission
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest

class PermissionUtils {


    companion object {


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
                ) { }.addOnFailureListener(
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

