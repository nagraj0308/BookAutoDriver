package com.book.auto.utils


import android.content.Context
import android.location.Location
import android.util.Log
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException


class Utils {
    companion object {
        fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
            val results = FloatArray(1)
            Location.distanceBetween(lat1, lon1, lat2, lon2, results)
            return String.format("%.2f", results[0] / 1000).toDouble()
        }

        fun printAdvertisingId( context : Context){
            CoroutineScope(Dispatchers.IO).launch {

                var idInfo: AdvertisingIdClient.Info? = null
                try {
                    idInfo = AdvertisingIdClient.getAdvertisingIdInfo(context)
                } catch (e: GooglePlayServicesNotAvailableException) {
                    e.printStackTrace()
                } catch (e: GooglePlayServicesRepairableException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                var advertId: String? = null
                try {
                    advertId = idInfo!!.id
                } catch (e: NullPointerException) {
                    e.printStackTrace()
                }
                Log.d("NAGRAJ", "$advertId")

            }
        }
    }
}