package com.book.auto.presentation.home


import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.book.auto.data.remote.reqres.Auto
import com.book.auto.domain.BVApi
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
    private val _autos = MutableLiveData<List<Auto>>()
    private val _lat = MutableLiveData(28.704060)
    private val _lon = MutableLiveData(77.102493)

    val autos: LiveData<List<Auto>> get() = _autos

    val lat: LiveData<Double> get() = _lat
    val lon: LiveData<Double> get() = _lon


    private fun getAutos() {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.getAllVehicle()
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

    fun onLocationUpdated(lat: Double, lon: Double) {
        _lat.value = lat
        _lon.value = lon
        getAutos()
    }
}