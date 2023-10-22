package com.book.admin.presentation.home

import android.annotation.SuppressLint
import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.book.admin.data.DataStore
import com.book.admin.data.remote.reqres.Auto
import com.book.admin.domain.BVApi
import com.book.admin.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@SuppressLint("MissingPermission")
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val api: BVApi
) : ViewModel() {
    private lateinit var dataStore: DataStore

    private val _autos = MutableLiveData<List<Auto>>()
    private val _password = MutableLiveData("")
    private val _statePos = MutableLiveData(1)


    val autos: LiveData<List<Auto>> get() = _autos
    val password: LiveData<String> get() = _password
    val statePos: LiveData<Int> get() = _statePos


    @OptIn(DelicateCoroutinesApi::class)
    fun initDataStore(activity: Activity) {
        dataStore = DataStore(activity)
        GlobalScope.launch(Dispatchers.IO) {
            dataStore.getPassword.collect {
                withContext(Dispatchers.Main) {
                    _password.value = it
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun logout() {
        GlobalScope.launch(Dispatchers.IO) {
            dataStore.setLogin(false, "") {
            }
        }
    }


    fun getAutos(pos: Int) {
        _statePos.value = pos
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.getAllAutoAdmin(Constants.vss[pos].code, password.value!!)
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

    fun changeAutoStatus(
        verificationState: String,
        vId: String, callback: () -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.changeAutoStatusV1(password.value!!, verificationState, vId)
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    if (it.isSuccessful && it.body() != null) {
                        if (it.body()!!.isTrue == 1) {
                            getAutos(_statePos.value!!)
                            callback()
                        }
                    }
                }
            }
        }
    }


//    private fun updateAutoLocation(
//        gId: String,
//        aLat: Double,
//        aLon: Double
//    ) {
//        CoroutineScope(Dispatchers.IO).launch {
//            runCatching {
//                api.updateVehicleLocation(
//                    VehicleLocationRequest(
//                        gId, aLat, aLon
//                    )
//                )
//            }.onSuccess {
//                withContext(Dispatchers.Main) {
//                    _isLocationUpdated.value = true
//                    _lat.value = aLat
//                    _lon.value = aLon
//                }
//            }
//        }
//    }


//    private fun deleteGaadiData(gaadiId: String, callback: (Boolean) -> Unit) {
//        CoroutineScope(Dispatchers.IO).launch {
//            runCatching {
//                api.deleteVehicleById(DeleteVehicleRequest(gaadiId))
//            }.onSuccess {
//                withContext(Dispatchers.Main) {
//                    showToast("Gaadi deleted successfully")
//                    getAutoDetails() {}
//                    callback(true)
//                }
//            }.onFailure {
//                withContext(Dispatchers.Main) {
//                    showToast("Gaadi not deleted")
//                    callback(false)
//                }
//            }
//        }
//    }

}