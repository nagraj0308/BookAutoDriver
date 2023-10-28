package com.book.auto.driver.presentation.pl


import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import com.book.auto.driver.BuildConfig
import com.book.auto.driver.R
import com.book.auto.driver.data.DataStore
import com.book.auto.driver.data.remote.reqres.VehicleLocationRequest
import com.book.auto.driver.presentation.home.HomeViewModel
import com.book.auto.driver.utils.Constants
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class SyncLocationService : Service() {

    private var _gmailId = MutableLiveData("")

    companion object {
        @OptIn(DelicateCoroutinesApi::class)
        fun start(context: Context) {
            val dataStore = DataStore(context)
            GlobalScope.launch(Dispatchers.IO) {
                dataStore.getEmail.collect {

                }
            }

            Intent(context, SyncLocationService::class.java).apply {
                context.startService(this)
            }
        }
    }


    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var locationClient: LocationClient

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        locationClient = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        start()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        val notification = NotificationCompat.Builder(this, "location")
            .setContentTitle("Location Synchronization")
            .setContentText("Sending device location to system")
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setOngoing(true)

        locationClient
            .getLocationUpdates(if (BuildConfig.DEBUG) Constants.SYNC_TIME * 12 * 1000L else Constants.SYNC_TIME * 1000L)
            .catch { e -> e.printStackTrace() }
            .onEach { location ->
                startForeground(1, notification.build())
                val lat = location.latitude
                val long = location.longitude
                Log.v("NAGRAJ", "$lat+$long")
            }
            .launchIn(serviceScope)

    }

    private fun updateAutoLocation(
        gId: String,
        aLat: Double,
        aLon: Double
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                api.updateVehicleLocation(
                    VehicleLocationRequest(
                        gId, aLat, aLon
                    )
                )
            }.onSuccess {
                stopForeground(true)
            }
        }
    }

    private fun stop() {
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

}