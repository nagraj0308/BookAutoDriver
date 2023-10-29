package com.book.auto.driver.presentation.synclocation


import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.book.auto.driver.BuildConfig
import com.book.auto.driver.PM
import com.book.auto.driver.R
import com.book.auto.driver.data.remote.reqres.VehicleLocationRequest
import com.book.auto.driver.domain.BVApi
import com.book.auto.driver.utils.Constants
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class SyncLocationService : Service() {

    @Inject
    lateinit var api: BVApi

    @Inject
    lateinit var pm: PM

    companion object {
        fun start(context: Context) {
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
            .getLocationUpdates(if (BuildConfig.DEBUG) Constants.SYNC_TIME * 1000L else Constants.SYNC_TIME * 12 * 1000L)
            .catch { e -> e.printStackTrace() }
            .onEach { location ->
                startForeground(1, notification.build())
                updateAutoLocation(pm.gmail!!, location.latitude, location.longitude)
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
            }.onFailure {
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