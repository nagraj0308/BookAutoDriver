package com.book.admin.presentation.home

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.book.admin.PM
import com.book.admin.data.remote.reqres.Auto
import com.book.admin.data.remote.reqres.Vehicle
import com.book.admin.domain.BVApi
import com.book.admin.utils.Constants
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
    @Inject
    lateinit var pm: PM

    private val _autos = MutableLiveData<List<Auto>>()
    private val _statePosAuto = MutableLiveData(1)

    private val _vehicles = MutableLiveData<List<Vehicle>>()
    private val _statePosVehicle = MutableLiveData(1)


    val autos: LiveData<List<Auto>> get() = _autos
    val vehicles: LiveData<List<Vehicle>> get() = _vehicles


    fun getAutos(pos: Int) {
        _statePosAuto.value = pos
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.getAllAutoAdmin(Constants.vss[pos].code, pm.password!!)
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
                api.changeAutoStatusV1(pm.password!!, verificationState, vId)
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    if (it.isSuccessful && it.body() != null) {
                        if (it.body()!!.isTrue == 1) {
                            getAutos(_statePosAuto.value!!)
                            callback()
                        }
                    }
                }
            }
        }
    }

    fun getVehicles(pos: Int) {
        _statePosVehicle.value = pos
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.getAllVehicleAdmin(Constants.vss[pos].code, pm.password!!)
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

    fun changeVehicleStatus(
        verificationState: String,
        vId: String, callback: () -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.changeVehicleStatusV1(pm.password!!, verificationState, vId)
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    if (it.isSuccessful && it.body() != null) {
                        if (it.body()!!.isTrue == 1) {
                            getVehicles(_statePosVehicle.value!!)
                            callback()
                        }
                    }
                }
            }
        }
    }

    fun changeVehicleAdminRemark(
        adminRemark: String,
        vId: String, callback: () -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.changeVehicleAdminRemark(pm.password!!, adminRemark, vId)
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    if (it.isSuccessful && it.body() != null) {
                        if (it.body()!!.isTrue == 1) {
                            getVehicles(_statePosVehicle.value!!)
                            callback()
                        }
                    }
                }
            }
        }
    }

    fun changeAutoAdminRemark(
        adminRemark: String,
        vId: String, callback: () -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.changeAutoAdminRemark(pm.password!!, adminRemark, vId)
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    if (it.isSuccessful && it.body() != null) {
                        if (it.body()!!.isTrue == 1) {
                            getAutos(_statePosAuto.value!!)
                            callback()
                        }
                    }
                }
            }
        }
    }


}