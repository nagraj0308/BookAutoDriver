package com.book.auto.presentation.home


import android.annotation.SuppressLint
import android.app.Activity
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.book.auto.data.remote.reqres.Auto
import com.book.auto.domain.BVApi
import com.book.auto.utils.PermissionUtils
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@SuppressLint("MissingPermission")
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val api: BVApi
) : ViewModel() {
    private val _autos = MutableLiveData<List<Auto>>()
    private val _isLocationUpdated = MutableLiveData(false)
    private val _lat = MutableLiveData(28.704060)
    private val _lon = MutableLiveData(77.102493)

    val autos: LiveData<List<Auto>> get() = _autos

    val lat: LiveData<Double> get() = _lat
    val lon: LiveData<Double> get() = _lon
    val isLocationUpdated: LiveData<Boolean> get() = _isLocationUpdated


    private fun getAutos() {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.getAllVehicle()
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    _autos.value = emptyList()
                    if (it.isSuccessful && it.body() != null) {
                        _autos.value = it.body()!!.data
                    }
                }
            }
        }
    }


    fun updateLocation(activity: Activity) {
        if (PermissionUtils.checkLocationEnabled(activity)) {
            if (PermissionUtils.checkLocationAccessPermission(activity)) {
                val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
                fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    location?.let {
                        _isLocationUpdated.value = true
                        _lat.value = it.latitude
                        _lon.value = it.longitude
                        getAutos()
                    }
                }
            } else {
                PermissionUtils.requestLocationAccessPermission(activity)
            }
        } else {
            PermissionUtils.requestLocationEnableRequest(activity)
        }
    }


}