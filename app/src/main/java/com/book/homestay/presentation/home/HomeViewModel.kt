package com.book.homestay.presentation.home


import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.book.homestay.PM
import com.book.homestay.data.remote.reqres.DeleteRequest
import com.book.homestay.data.remote.reqres.GetHomestayByIdRequest
import com.book.homestay.data.remote.reqres.GetHomestayRequest
import com.book.homestay.data.remote.reqres.Homestay
import com.book.homestay.data.remote.reqres.HomestayRequest
import com.book.homestay.data.remote.reqres.Img1Request
import com.book.homestay.data.remote.reqres.Img2Request
import com.book.homestay.data.remote.reqres.Img3Request
import com.book.homestay.data.remote.reqres.Img4Request
import com.book.homestay.domain.Api
import com.book.homestay.utils.FBS
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


    private val _homestay = MutableLiveData(Homestay())
    private val _houses = MutableLiveData<List<Homestay>>()
    private val _isLocationUpdated = MutableLiveData(false)
    private val _lat = MutableLiveData(0.0)
    private val _lon = MutableLiveData(0.0)
    private val _imgUrl1 = MutableLiveData("")
    private val _imgUrl2 = MutableLiveData("")
    private val _imgUrl3 = MutableLiveData("")
    private val _imgUrl4 = MutableLiveData("")


    val homestay: LiveData<Homestay> get() = _homestay
    val houses: LiveData<List<Homestay>> get() = _houses
    val lat: LiveData<Double> get() = _lat
    val lon: LiveData<Double> get() = _lon
    val img1: LiveData<String> get() = _imgUrl1
    val img2: LiveData<String> get() = _imgUrl2
    val img3: LiveData<String> get() = _imgUrl3
    val img4: LiveData<String> get() = _imgUrl4


    val isLocationUpdated: LiveData<Boolean> get() = _isLocationUpdated


    private fun getAllHomestay() {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.getAllActiveHomestay(
                    GetHomestayRequest(
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
        getAllHomestay()
    }

    fun setImg1(url: String) {
        _imgUrl1.value = url
        updateHomestayImg1()
    }

    fun setImg2(url: String) {
        _imgUrl2.value = url
        updateHomestayImg2()
    }

    fun setImg3(url: String) {
        _imgUrl3.value = url
        updateHomestayImg3()
    }

    fun setImg4(url: String) {
        _imgUrl4.value = url
        updateHomestayImg4()
    }

    fun insertHomestay(
        gName: String, gAddress: String, gMobile: String, gRate: String, callback: (Boolean) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.insertHomestay(
                    HomestayRequest(
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
                    getAutoHomestay() {}
                    callback(true)
                }
            }.onFailure {
                withContext(Dispatchers.Main) {
                    callback(false)
                }
            }
        }
    }


    fun updateHomestayData(
        gName: String, gAddress: String, gMobile: String, gRate: String, callback: (Boolean) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.updateHomestay(
                    HomestayRequest(
                        pm.gmail, gName, gAddress, 0.0, 0.0, gMobile, gRate, "", "", "", ""
                    )
                )
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    getAutoHomestay() {}
                    callback(true)
                }
            }.onFailure {
                withContext(Dispatchers.Main) {
                    callback(false)
                }
            }
        }
    }


    fun getAutoHomestay(callback: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.getHomestayById(GetHomestayByIdRequest(pm.gmail))
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    if (it.isSuccessful) {
                        if (it.body() != null) {
                            callback(true)
                            _homestay.value = it.body()!!.data
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


    fun deleteHomestay(callback: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            deleteHomestayImage(_homestay.value!!._id + "1") {}
        }
        CoroutineScope(Dispatchers.IO).launch {
            deleteHomestayImage(_homestay.value!!._id + "2") {}
        }
        CoroutineScope(Dispatchers.IO).launch {
            deleteHomestayImage(_homestay.value!!._id + "3") {}
        }
        CoroutineScope(Dispatchers.IO).launch {
            deleteHomestayImage(_homestay.value!!._id + "4") {}
        }
        deleteHomestayData {
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


    private fun deleteHomestayImage(
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


    private fun deleteHomestayData(
        callback: (Boolean) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.deleteHomestayById(DeleteRequest(pm.gmail))
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    getAutoHomestay() {}
                    callback(true)
                }
            }.onFailure {
                withContext(Dispatchers.Main) {
                    callback(false)
                }
            }
        }
    }

    private fun updateHomestayImg1() {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.updateImg1(Img1Request(pm.gmail, _imgUrl1.value))
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    getAutoHomestay() {}
                }
            }
        }
    }

    private fun updateHomestayImg2() {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.updateImg2(Img2Request(pm.gmail, _imgUrl2.value))
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    getAutoHomestay() {}
                }
            }
        }
    }

    private fun updateHomestayImg3() {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.updateImg3(Img3Request(pm.gmail, _imgUrl3.value))
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    getAutoHomestay() {}
                }
            }
        }
    }

    private fun updateHomestayImg4() {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.updateImg4(Img4Request(pm.gmail, _imgUrl4.value))
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    getAutoHomestay() {}
                }
            }
        }
    }

}