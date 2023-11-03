package com.book.auto.presentation.home


import android.annotation.SuppressLint
import android.app.Activity
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.book.auto.data.remote.reqres.Vehicle
import com.book.auto.domain.BVApi
import com.book.auto.utils.PermissionUtils
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@SuppressLint("MissingPermission")
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val api: BVApi
) : ViewModel() {

    private val _vehicle = MutableLiveData(Vehicle())
    private val _isLocationUpdated = MutableLiveData(false)
    private val _lat = MutableLiveData(0.0)
    private val _lon = MutableLiveData(0.0)

    val vehicle: LiveData<Vehicle> get() = _vehicle

    val lat: LiveData<Double> get() = _lat
    val lon: LiveData<Double> get() = _lon
    val isLocationUpdated: LiveData<Boolean> get() = _isLocationUpdated


    fun updateLocation(activity: Activity) {
        if (PermissionUtils.checkLocationEnabled(activity)) {
            if (PermissionUtils.checkLocationAccessPermission(activity)) {
                val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
                fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    location?.let {
                        _isLocationUpdated.value = true
                        _lat.value = it.latitude
                        _lon.value = it.longitude
//                        updateAutoLocation(pm.gmail!!, it.latitude, it.longitude)
                    }
                }
            }
        } else {
            PermissionUtils.requestLocationEnableRequest(activity)
        }
    }


}