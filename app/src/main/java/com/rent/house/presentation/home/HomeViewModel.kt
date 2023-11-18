package com.rent.house.presentation.home


import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import com.google.firebase.storage.UploadTask
import com.rent.house.PM
import com.rent.house.data.remote.reqres.DeleteVehicleRequest
import com.rent.house.data.remote.reqres.GetVehicleByGmailIdRequest
import com.rent.house.data.remote.reqres.GetVehicleRequest
import com.rent.house.data.remote.reqres.Vehicle
import com.rent.house.data.remote.reqres.VehicleRequest
import com.rent.house.domain.BVApi
import com.rent.house.utils.FBS
import com.rent.house.utils.PermissionUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject


@SuppressLint("MissingPermission")
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val api: BVApi
) : ViewModel() {

    @Inject
    lateinit var pm: PM


    private val _vehicle = MutableLiveData(Vehicle())
    private val _vehicles = MutableLiveData<List<Vehicle>>()
    private val _isLocationUpdated = MutableLiveData(false)
    private val _lat = MutableLiveData(0.0)
    private val _lon = MutableLiveData(0.0)

    val vehicle: LiveData<Vehicle> get() = _vehicle
    val vehicles: LiveData<List<Vehicle>> get() = _vehicles
    val lat: LiveData<Double> get() = _lat
    val lon: LiveData<Double> get() = _lon
    val isLocationUpdated: LiveData<Boolean> get() = _isLocationUpdated


    private fun getAllVehicle() {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.getAllVehicle(
                    GetVehicleRequest(
                        _lat.value,
                        _lon.value
                    )
                )
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    _vehicles.value = emptyList()
                    if (it.isSuccessful && it.body() != null) {
                        _vehicles.value = it.body()!!.data
                    }
                }
            }
        }
    }


    fun insertFSImage(
        gTypeId: Int,
        gNumber: String,
        gDriver: String,
        gMobile: String,
        gRate: String,
        bitmap: Bitmap, callback: (Boolean) -> Unit
    ) {


        viewModelScope.launch(Dispatchers.IO) {
            val ref = FBS.getReference(pm.gmail!!)
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos)
            val data = baos.toByteArray()
            val uploadTask: UploadTask = ref.putBytes(data)
            uploadTask.addOnFailureListener {
                CoroutineScope(Dispatchers.IO).launch {
                    withContext(Dispatchers.Main) {
                        callback(false)
                    }
                }
            }.addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener {
                    insertGaadi(
                        pm.gmail!!,
                        gTypeId,
                        gNumber,
                        gDriver,
                        gMobile,
                        gRate,
                        it.toString()
                    ) {
                        CoroutineScope(Dispatchers.IO).launch {
                            withContext(Dispatchers.Main) {
                                callback(it)
                            }
                        }
                    }
                }.addOnFailureListener {
                    CoroutineScope(Dispatchers.IO).launch {
                        withContext(Dispatchers.Main) {
                            callback(false)
                        }
                    }
                }
            }
        }
    }

    private fun insertGaadi(
        gId: String,
        gTypeId: Int,
        gNumber: String,
        gDriver: String,
        gMobile: String,
        gRate: String,
        url: String, callback: (Boolean) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.insertVehicle(
                    VehicleRequest(
                        gId,
                        false,
                        gDriver,
                        url,
                        _lat.value,
                        _lon.value,
                        gMobile,
                        gNumber, gRate,
                        gTypeId
                    )
                )
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    getAutoDetails() {}
                    callback(true)
                }
            }.onFailure {
                withContext(Dispatchers.Main) {
                    callback(false)
                }
            }
        }
    }


    fun updateFSImage(
        gTypeId: Int,
        gNumber: String,
        gDriver: String,
        gMobile: String,
        gRate: String,
        bitmap: Bitmap, callback: (Boolean) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val ref = FBS.getReference(pm.gmail!!)
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos)
            val data = baos.toByteArray()
            val uploadTask: UploadTask = ref.putBytes(data)
            uploadTask.addOnFailureListener {
                CoroutineScope(Dispatchers.IO).launch {
                    withContext(Dispatchers.Main) {
                        callback(false)
                    }
                }
            }.addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener {
                    updateGaadiData(
                        pm.gmail!!,
                        gTypeId,
                        gNumber,
                        gDriver,
                        gMobile,
                        gRate,
                        it.toString()
                    ) {
                        CoroutineScope(Dispatchers.IO).launch {
                            withContext(Dispatchers.Main) {
                                callback(it)
                            }
                        }
                    }
                }.addOnFailureListener {
                    CoroutineScope(Dispatchers.IO).launch {
                        withContext(Dispatchers.Main) {
                            callback(false)
                        }
                    }
                }
            }
        }
    }


    private fun updateGaadiData(
        gId: String,
        gTypeId: Int,
        gNumber: String,
        gDriver: String,
        gMobile: String,
        gRate: String,
        url: String, callback: (Boolean) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.updateVehicle(
                    VehicleRequest(
                        gId,
                        false,
                        gDriver,
                        url,
                        _lat.value,
                        _lon.value,
                        gMobile,
                        gNumber, gRate,
                        gTypeId
                    )
                )
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    getAutoDetails() {}
                    callback(true)
                }
            }.onFailure {
                withContext(Dispatchers.Main) {
                    callback(false)
                }
            }
        }
    }


    fun getAutoDetails(callback: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.getVehicleByGmailId(GetVehicleByGmailIdRequest(pm.gmail))
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    if (it.isSuccessful) {
                        if (it.body() != null) {
                            callback(true)
                            _vehicle.value = it.body()!!.data
                        }
                    }
                }
            }.onFailure {
                withContext(Dispatchers.Main) {
                    callback(true)
                }
            }
        }
    }


    fun updateLocation(activity: Activity, callback: (Boolean) -> Unit) {
        if (PermissionUtils.checkLocationEnabled(activity)) {
            if (PermissionUtils.checkLocationAccessPermission(activity)) {
                val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
                fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    location?.let {
                        _isLocationUpdated.value = true
                        _lat.value = it.latitude
                        _lon.value = it.longitude
                        getAllVehicle()
                        callback(true)
                    }
                }
            } else {
                PermissionUtils.requestLocationAccessPermission(activity)
            }
        } else {
            PermissionUtils.requestLocationEnableRequest(activity)
        }
    }


    fun deleteAuto(callback: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            deleteGaadiImage(_vehicle.value!!._id) { result ->
                if (result) {
                    deleteGaadiData(_vehicle.value!!._id) {
                        if (it) {
                            CoroutineScope(Dispatchers.IO).launch {
                                withContext(Dispatchers.Main) {
                                    callback(true)

                                }
                            }
                        } else {
                            CoroutineScope(Dispatchers.IO).launch {
                                withContext(Dispatchers.Main) {
                                    callback(false)
                                }
                            }
                        }
                    }
                } else {
                    CoroutineScope(Dispatchers.IO).launch {
                        withContext(Dispatchers.Main) {
                            callback(false)
                        }
                    }
                }
            }
        }
    }


    private fun deleteGaadiImage(
        gaadiId: String,
        callback: (Boolean) -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val ref = FBS.getReference(gaadiId)
            ref.delete()
                .addOnSuccessListener {
                    CoroutineScope(Dispatchers.IO).launch {
                        withContext(Dispatchers.Main) {
                            callback(true)
                        }
                    }
                }.addOnFailureListener {
                    CoroutineScope(Dispatchers.IO).launch {
                        withContext(Dispatchers.Main) {
                            callback(false)
                        }
                    }

                }
        }
    }


    private fun deleteGaadiData(
        gaadiId: String,
        callback: (Boolean) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.deleteVehicleById(DeleteVehicleRequest(gaadiId))
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    getAutoDetails() {}
                    callback(true)
                }
            }.onFailure {
                withContext(Dispatchers.Main) {
                    callback(false)
                }
            }
        }
    }

}