package com.rent.house.presentation.home


import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rent.house.PM
import com.rent.house.data.remote.reqres.DeleteRequest
import com.rent.house.data.remote.reqres.GetHouseByIdRequest
import com.rent.house.data.remote.reqres.GetHouseRequest
import com.rent.house.data.remote.reqres.House
import com.rent.house.data.remote.reqres.HouseRequest
import com.rent.house.data.remote.reqres.Img1Request
import com.rent.house.data.remote.reqres.Img2Request
import com.rent.house.data.remote.reqres.Img3Request
import com.rent.house.data.remote.reqres.Img4Request
import com.rent.house.domain.Api
import com.rent.house.utils.FBS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    private val _imgUrl1 = MutableLiveData("")
    private val _imgUrl2 = MutableLiveData("")
    private val _imgUrl3 = MutableLiveData("")
    private val _imgUrl4 = MutableLiveData("")


    val house: LiveData<House> get() = _house
    val houses: LiveData<List<House>> get() = _houses
    val lat: LiveData<Double> get() = _lat
    val lon: LiveData<Double> get() = _lon
    val img1: LiveData<String> get() = _imgUrl1
    val img2: LiveData<String> get() = _imgUrl2
    val img3: LiveData<String> get() = _imgUrl3
    val img4: LiveData<String> get() = _imgUrl4


    val isLocationUpdated: LiveData<Boolean> get() = _isLocationUpdated


    private fun getAllHouse() {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.getAllActiveHouse(
                    GetHouseRequest(
                        _lat.value, _lon.value
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

    fun onLocationUpdated(lat: Double, lon: Double) {
        _isLocationUpdated.value = true
        _lat.value = lat
        _lon.value = lon
        getAllHouse()
    }

    fun setImg1(url: String) {
        _imgUrl1.value = url
        updateHouseImg1()
    }

    fun setImg2(url: String) {
        _imgUrl2.value = url
        updateHouseImg2()
    }

    fun setImg3(url: String) {
        _imgUrl3.value = url
        updateHouseImg3()
    }

    fun setImg4(url: String) {
        _imgUrl4.value = url
        updateHouseImg4()
    }

    fun insertHouse(
        gName: String, gAddress: String, gMobile: String, gRate: String, callback: (Boolean) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.insertHouse(
                    HouseRequest(
                        pm.gmail,
                        gName,
                        gAddress,
                        _lat.value,
                        _lon.value,
                        gMobile,
                        gRate,
                        _imgUrl1.value,
                        _imgUrl2.value,
                        _imgUrl3.value,
                        _imgUrl4.value
                    )
                )
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    getAutoHouse() {}
                    callback(true)
                }
            }.onFailure {
                withContext(Dispatchers.Main) {
                    callback(false)
                }
            }
        }
    }


    fun updateHouseData(
        gName: String, gAddress: String, gMobile: String, gRate: String, callback: (Boolean) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.updateHouse(
                    HouseRequest(
                        pm.gmail, gName, gAddress, 0.0, 0.0, gMobile, gRate, "", "", "", ""
                    )
                )
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    getAutoHouse() {}
                    callback(true)
                }
            }.onFailure {
                withContext(Dispatchers.Main) {
                    callback(false)
                }
            }
        }
    }


    fun getAutoHouse(callback: (Boolean) -> Unit) {
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


    fun deleteHouse(callback: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            deleteHouseImage(_house.value!!._id + "1") {}
        }
        CoroutineScope(Dispatchers.IO).launch {
            deleteHouseImage(_house.value!!._id + "2") {}
        }
        CoroutineScope(Dispatchers.IO).launch {
            deleteHouseImage(_house.value!!._id + "3") {}
        }
        CoroutineScope(Dispatchers.IO).launch {
            deleteHouseImage(_house.value!!._id + "4") {}
        }
        deleteHouseData {
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
    }


    private fun deleteHouseImage(
        gId: String,
        callback: (Boolean) -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val ref = FBS.getReference(gId)
            ref.delete().addOnSuccessListener {
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


    private fun deleteHouseData(
        callback: (Boolean) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.deleteHouseById(DeleteRequest(pm.gmail))
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    getAutoHouse() {}
                    callback(true)
                }
            }.onFailure {
                withContext(Dispatchers.Main) {
                    callback(false)
                }
            }
        }
    }

    private fun updateHouseImg1() {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.updateImg1(Img1Request(pm.gmail, _imgUrl1.value))
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    getAutoHouse() {}
                }
            }
        }
    }

    private fun updateHouseImg2() {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.updateImg2(Img2Request(pm.gmail, _imgUrl2.value))
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    getAutoHouse() {}
                }
            }
        }
    }

    private fun updateHouseImg3() {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.updateImg3(Img3Request(pm.gmail, _imgUrl3.value))
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    getAutoHouse() {}
                }
            }
        }
    }

    private fun updateHouseImg4() {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.updateImg4(Img4Request(pm.gmail, _imgUrl4.value))
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    getAutoHouse() {}
                }
            }
        }
    }

}