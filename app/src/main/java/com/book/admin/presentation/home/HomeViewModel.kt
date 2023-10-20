package com.book.admin.presentation.home

import android.annotation.SuppressLint
import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.book.admin.data.DataStore
import com.book.admin.data.remote.reqres.Auto
import com.book.admin.domain.BVApi
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

    private val _autos = MutableLiveData<MutableList<Auto>>()
    private val _password = MutableLiveData("")

    val autos: MutableLiveData<MutableList<Auto>> get() = _autos
    val password: LiveData<String> get() = _password


    @OptIn(DelicateCoroutinesApi::class)
    fun initDataStore(activity: Activity) {
        dataStore = DataStore(activity)
        GlobalScope.launch(Dispatchers.IO) {
            dataStore.getPassword.collect {
                withContext(Dispatchers.Main) {
                    _password.value = it
//                    getPendingAutos()
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


    private fun getPendingAutos() {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.getAllAutoAdmin("nagraj0308")
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    _autos.value!!.clear()
                    if (it.isSuccessful && it.body() != null) {
//                        _autos.value!!.addAll(it.body()!!.data)
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