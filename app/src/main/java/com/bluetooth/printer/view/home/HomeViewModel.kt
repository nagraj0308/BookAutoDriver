package com.bluetooth.printer.view.home


import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bluetooth.printer.PM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@SuppressLint("MissingPermission")
@HiltViewModel
class HomeViewModel @Inject constructor(
) : ViewModel() {

    @Inject
    lateinit var pm: PM


    private val _isLocationUpdated = MutableLiveData(false)
    private val _lat = MutableLiveData(0.0)
    private val _lon = MutableLiveData(0.0)
    private val _imgUrl1 = MutableLiveData("")
    private val _imgUrl2 = MutableLiveData("")
    private val _imgUrl3 = MutableLiveData("")
    private val _imgUrl4 = MutableLiveData("")


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

            }.onSuccess {

            }
        }
    }

    fun onLocationUpdated(lat: Double, lon: Double) {
        _isLocationUpdated.value = true
        _lat.value = lat
        _lon.value = lon
        getAllHouse()
    }












}