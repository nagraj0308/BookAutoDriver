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
import com.rent.house.data.remote.reqres.DeleteRequest
import com.rent.house.data.remote.reqres.GetHouseByIdRequest
import com.rent.house.data.remote.reqres.GetHouseRequest
import com.rent.house.data.remote.reqres.House
import com.rent.house.data.remote.reqres.HouseRequest
import com.rent.house.domain.Api
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
    private val api: Api
) : ViewModel() {

    @Inject
    lateinit var pm: PM


    private val _house = MutableLiveData(House())
    private val _houses = MutableLiveData<List<House>>()
    private val _isLocationUpdated = MutableLiveData(false)
    private val _lat = MutableLiveData(0.0)
    private val _lon = MutableLiveData(0.0)

    val house: LiveData<House> get() = _house
    val houses: LiveData<List<House>> get() = _houses
    val lat: LiveData<Double> get() = _lat
    val lon: LiveData<Double> get() = _lon
    val isLocationUpdated: LiveData<Boolean> get() = _isLocationUpdated


    private fun getAllHouse() {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.getAllActiveHouse(
                    GetHouseRequest(
                        _lat.value,
                        _lon.value
                    )
                )
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    _houses.value = emptyList()
                    if (it.isSuccessful && it.body() != null) {
                        _houses.value = it.body()!!.data
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
        gName: String,
        gMobile: String,
        gRate: String,
        gImg1: String,
        gImg2: String,
        gImg3: String,
        gImg4: String,
        callback: (Boolean) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.insertHouse(
                    HouseRequest(
                        gId, gName,
                        _lat.value,
                        _lon.value,
                        gMobile,
                        gRate,
                        gImg1,
                        gImg2,
                        gImg3,
                        gImg4
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
        gMobile: String,
        gRate: String,
        bitmap1: Bitmap,
        bitmap2: Bitmap,
        bitmap3: Bitmap,
        bitmap4: Bitmap,
        callback: (Boolean) -> Unit
    ) {
        var img1:String
        viewModelScope.launch(Dispatchers.IO) {
            val ref = FBS.getReference(pm.gmail!! + "1")
            val baos = ByteArrayOutputStream()
            bitmap1.compress(Bitmap.CompressFormat.JPEG, 60, baos)
            val data = baos.toByteArray()
            val uploadTask: UploadTask = ref.putBytes(data)
            uploadTask.addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener {

                }
            }
        }
    }

    updateGaadiData(
    pm.gmail!!,
    gMobile,
    gRate,
    it.toString(),
    "",
    "",
    "",
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                callback(it)
            }
        }
    }


    private fun updateGaadiData(
        gName: String,
        gMobile: String,
        gRate: String,
        gImg1: String,
        gImg2: String,
        gImg3: String,
        gImg4: String,
        callback: (Boolean) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.updateHouse(
                    HouseRequest(
                        pm.gmail, gName,
                        _lat.value,
                        _lon.value,
                        gMobile,
                        gRate,
                        gImg1,
                        gImg2,
                        gImg3,
                        gImg4
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
                api.getHouseById(GetHouseByIdRequest(pm.gmail))
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    if (it.isSuccessful) {
                        if (it.body() != null) {
                            callback(true)
                            _house.value = it.body()!!.data
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
                        getAllHouse()
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
            deleteGaadiImage(_house.value!!._id) { result ->
                if (result) {
                    deleteGaadiData(_house.value!!._id) {
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
                api.deleteHouseById(DeleteRequest(gaadiId))
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