package com.book.auto.driver.presentation.home


import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.book.auto.driver.data.DataStore
import com.book.auto.driver.data.remote.reqres.DeleteVehicleRequest
import com.book.auto.driver.data.remote.reqres.GetVehicleByGmailIdRequest
import com.book.auto.driver.data.remote.reqres.Vehicle
import com.book.auto.driver.data.remote.reqres.VehicleRequest
import com.book.auto.driver.domain.BVApi
import com.book.auto.driver.utils.FBS
import com.book.auto.driver.utils.PermissionUtils
import com.google.android.gms.location.LocationServices
import com.google.firebase.storage.UploadTask
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject


data class HomeState(
    var email: String = "",
    var name: String = "",
    var lat: Double = 28.656473,
    var lon: Double = 77.242943,
    var isLocationUpdated: Boolean = false,
)


@SuppressLint("MissingPermission")
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val api: BVApi
) : ViewModel() {
    private lateinit var dataStore: DataStore

    private val _vehicle = MutableLiveData(Vehicle())
    private val _state = MutableLiveData(HomeState())
    private var _showProgress = MutableLiveData(false)
    private var _toastMsg = MutableLiveData("")
    private var _showToast = MutableLiveData(false)
    val readShowProgress: LiveData<Boolean> get() = _showProgress
    val readVehicle: LiveData<Vehicle> get() = _vehicle
    val readState: LiveData<HomeState> get() = _state
    val readToastMsg: LiveData<String> get() = _toastMsg
    val readShowToast: LiveData<Boolean> get() = _showToast


    @OptIn(DelicateCoroutinesApi::class)
    fun initDataStore(activity: Activity) {
        dataStore = DataStore(activity)
        GlobalScope.launch(Dispatchers.IO) {
            dataStore.getEmail.collect {
                _state.value!!.email = it
                withContext(Dispatchers.Main) {
                    getAutoDetails { }
                }
            }
        }
        GlobalScope.launch(Dispatchers.IO) {
            dataStore.getName.collect {
                _state.value!!.name = it
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun logout() {
        GlobalScope.launch(Dispatchers.IO) {
            dataStore.setLogin(false)
        }
    }

    fun showProgress() {
        _showProgress.value = true
    }

    fun hideProgress() {
        _showProgress.value = false
    }


    private fun showToast(msg: String) {
        _toastMsg.value = msg
        _showToast.value = true
    }

    fun insertFSImage(
        gId: String,
        gName: String,
        gType: String,
        gRate: String,
        gNumber: String,
        gDriver: String,
        gMobile: String,
        gDeactivated: Boolean,
        bitmap: Bitmap, callback: (Boolean) -> Unit
    ) {


        viewModelScope.launch(Dispatchers.IO) {
            val ref = FBS.getReference(gId)
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos)
            val data = baos.toByteArray()
            val uploadTask: UploadTask = ref.putBytes(data)
            uploadTask.addOnFailureListener {
                _showProgress.value = false
                CoroutineScope(Dispatchers.IO).launch {
                    withContext(Dispatchers.Main) {
                        showToast(it.toString())
                        callback(false)
                    }
                }
            }.addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener {
                    insertGaadi(
                        gId,
                        gName,
                        gType,
                        gRate,
                        gNumber,
                        gDriver,
                        gMobile,
                        gDeactivated,
                        it.toString()
                    ) {
                        _showProgress.value = false
                        CoroutineScope(Dispatchers.IO).launch {
                            withContext(Dispatchers.Main) {
                                callback(it)
                            }
                        }
                    }
                }.addOnFailureListener {
                    _showProgress.value = false
                    CoroutineScope(Dispatchers.IO).launch {
                        withContext(Dispatchers.Main) {
                            showToast(it.toString())
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
        gType: String,
        gRate: String,
        gNumber: String,
        gDriver: String,
        gMobile: String,
        gDeactivated: Boolean,
        url: String, callback: (Boolean) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.insertVehicle(
                    VehicleRequest(
                        gId,
                        gName,
                        gDeactivated,
                        gDriver,
                        url,
                        _state.value!!.lat,
                        _state.value!!.lon,
                        gMobile,
                        gNumber,
                        gRate,
                        gType
                    )
                )
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    showToast("Gaadi added successfully")
                    getAutoDetails() {}
                    callback(true)
                }
            }.onFailure {
                withContext(Dispatchers.Main) {
                    showToast("Gaadi not added")
                    callback(false)
                }
            }
        }
    }


    fun updateFSImage(
        gId: String,
        gName: String,
        gType: String,
        gRate: String,
        gNumber: String,
        gDriver: String,
        gMobile: String,
        gDeactivated: Boolean,
        bitmap: Bitmap, callback: (Boolean) -> Unit
    ) {
        _showProgress.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val ref = FBS.getReference(gId)
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos)
            val data = baos.toByteArray()
            val uploadTask: UploadTask = ref.putBytes(data)
            uploadTask.addOnFailureListener {
                _showProgress.value = false
                CoroutineScope(Dispatchers.IO).launch {
                    withContext(Dispatchers.Main) {
                        showToast(it.toString())
                        callback(false)
                    }
                }
            }.addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener {
                    updateGaadiData(
                        gId,
                        gName,
                        gType,
                        gRate,
                        gNumber,
                        gDriver,
                        gMobile,
                        gDeactivated,
                        it.toString()
                    ) {
                        _showProgress.value = false
                        CoroutineScope(Dispatchers.IO).launch {
                            withContext(Dispatchers.Main) {
                                callback(it)
                            }
                        }
                    }
                }.addOnFailureListener {
                    _showProgress.value = false
                    CoroutineScope(Dispatchers.IO).launch {
                        withContext(Dispatchers.Main) {
                            showToast(it.toString())
                            callback(false)
                        }
                    }
                }
            }
        }
    }


    private fun updateGaadiData(
        gId: String,
        gName: String,
        gType: String,
        gRate: String,
        gNumber: String,
        gDriver: String,
        gMobile: String,
        gDeactivated: Boolean,
        url: String, callback: (Boolean) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.updateVehicle(
                    VehicleRequest(
                        gId,
                        gName,
                        gDeactivated,
                        gDriver,
                        url,
                        _state.value!!.lat,
                        _state.value!!.lon,
                        gMobile,
                        gNumber,
                        gRate,
                        gType
                    )
                )
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    showToast("Gaadi updated successfully")
                    getAutoDetails() {}
                    callback(true)
                }
            }.onFailure {
                withContext(Dispatchers.Main) {
                    showToast("Gaadi not updated")
                    callback(false)
                }
            }
        }
    }


    fun getAutoDetails(callback: (Boolean) -> Unit) {
        _showProgress.value = true
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.getVehicleByGmailId(GetVehicleByGmailIdRequest(_state.value!!.email))
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    _showProgress.value = false
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
                    _showProgress.value = false
                    showToast(it.toString())
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
                        _state.value!!.isLocationUpdated = true
                        _state.value!!.lat = it.latitude
                        _state.value!!.lon = it.longitude
                        Log.v("NAGRAJ", it.latitude.toString() + " " + it.longitude.toString())
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
        _showProgress.value = true
        CoroutineScope(Dispatchers.IO).launch {
            deleteGaadiImage(_vehicle.value!!._id) { result ->
                if (result) {
                    deleteGaadiData(_vehicle.value!!._id) {
                        if (it) {
                            _showProgress.value = false
                            CoroutineScope(Dispatchers.IO).launch {
                                withContext(Dispatchers.Main) {
                                    callback(true)
                                }
                            }
                        } else {
                            _showProgress.value = false
                            CoroutineScope(Dispatchers.IO).launch {
                                withContext(Dispatchers.Main) {
                                    callback(false)
                                }
                            }
                        }
                    }
                } else {
                    _showProgress.value = false
                    CoroutineScope(Dispatchers.IO).launch {
                        withContext(Dispatchers.Main) {
                            callback(false)
                        }
                    }
                }
            }
        }
    }


    private fun deleteGaadiImage(gaadiId: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val ref = FBS.getReference(gaadiId)
            ref.delete()
                .addOnSuccessListener {
                    CoroutineScope(Dispatchers.IO).launch {
                        withContext(Dispatchers.Main) {
                            showToast("Related Image deleted successfully")
                            callback(true)
                        }
                    }
                }.addOnFailureListener {
                    CoroutineScope(Dispatchers.IO).launch {
                        withContext(Dispatchers.Main) {
                            showToast("Could not Image deleted successfully")
                            callback(false)
                        }
                    }

                };
        }
    }


    private fun deleteGaadiData(gaadiId: String, callback: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.deleteVehicleById(DeleteVehicleRequest(gaadiId))
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    showToast("Gaadi deleted successfully")
                    getAutoDetails() {}
                    callback(true)
                }
            }.onFailure {
                withContext(Dispatchers.Main) {
                    showToast("Gaadi not deleted")
                    callback(false)
                }
            }
        }
    }

}