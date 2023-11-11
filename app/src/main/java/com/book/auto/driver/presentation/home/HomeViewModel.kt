package com.book.auto.driver.presentation.home


import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.book.auto.driver.PM
import com.book.auto.driver.data.remote.reqres.DeleteVehicleRequest
import com.book.auto.driver.data.remote.reqres.GetVehicleByGmailIdRequest
import com.book.auto.driver.data.remote.reqres.Vehicle
import com.book.auto.driver.data.remote.reqres.VehicleLocationRequest
import com.book.auto.driver.data.remote.reqres.VehicleRequest
import com.book.auto.driver.data.remote.reqres.VerificationStatusRequest
import com.book.auto.driver.domain.BVApi
import com.book.auto.driver.utils.FBS
import com.book.auto.driver.utils.PermissionUtils
import com.google.android.gms.location.LocationServices
import com.google.firebase.storage.UploadTask
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
    private val _isLocationUpdated = MutableLiveData(false)
    private val _lat = MutableLiveData(0.0)
    private val _lon = MutableLiveData(0.0)

    val vehicle: LiveData<Vehicle> get() = _vehicle

    val lat: LiveData<Double> get() = _lat
    val lon: LiveData<Double> get() = _lon
    val isLocationUpdated: LiveData<Boolean> get() = _isLocationUpdated


    fun insertFSImage(
        gType: String,
        gNumber: String,
        gDriver: String,
        gMobile: String,
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
                        gType,
                        gNumber,
                        gDriver,
                        gMobile,
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
        gType: String,
        gNumber: String,
        gDriver: String,
        gMobile: String,
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
                        gNumber,
                        gType
                    )
                )
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    getAutoDetails {}
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
        gType: String,
        gNumber: String,
        gDriver: String,
        gMobile: String,
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
                        gType,
                        gNumber,
                        gDriver,
                        gMobile,
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
        gType: String,
        gNumber: String,
        gDriver: String,
        gMobile: String,
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
                        gNumber,
                        gType
                    )
                )
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    getAutoDetails {}
                    callback(true)
                }
            }.onFailure {
                withContext(Dispatchers.Main) {
                    callback(false)
                }
            }
        }
    }

    private fun updateAutoLocation(
        gId: String,
        aLat: Double,
        aLon: Double
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.updateVehicleLocation(
                    VehicleLocationRequest(
                        gId, aLat, aLon
                    )
                )
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    _isLocationUpdated.value = true
                    _lat.value = aLat
                    _lon.value = aLon
                }
            }
        }
    }


    fun updateAutoActivation(
        deactivated: Boolean
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.updateAutoActiveStatus(
                    VerificationStatusRequest(
                        pm.gmail, deactivated
                    )
                )
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    getAutoDetails { }
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


    fun updateLocation(activity: Activity) {
        if (PermissionUtils.checkLocationEnabled(activity)) {
            if (PermissionUtils.checkLocationAccessPermission(activity)) {
                val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
                fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    location?.let {
                        _isLocationUpdated.value = true
                        _lat.value = it.latitude
                        _lon.value = it.longitude
                        updateAutoLocation(pm.gmail!!, it.latitude, it.longitude)
                    }
                }
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
        callback: (Boolean) -> Unit
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
                    getAutoDetails {}
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