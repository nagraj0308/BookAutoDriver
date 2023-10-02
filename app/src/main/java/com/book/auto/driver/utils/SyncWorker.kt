package com.book.auto.driver.utils

import android.Manifest
import android.app.Activity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.book.auto.driver.BuildConfig
import com.book.auto.driver.R
import com.book.auto.driver.presentation.home.HomeViewModel
import java.util.concurrent.TimeUnit


class SyncWorker(private var context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    val CHANNEL_ID = "channelID"
    val CHANNEL_NAME = "channelName"
    val NOTIF_ID = 0



    fun checkLocationEnabled(): Boolean {
        val mLocationManager =
           context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
    override fun doWork(): Result {




        Log.v("NAGRAJ","AB");

//        val notif = NotificationCompat.Builder(this,CHANNEL_ID)
//            .setContentTitle("Sample Title")
//            .setContentText("This is sample body notif")
//            .setSmallIcon(R.mipmap.ic_launcher_round)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .build()
//
//
//        val notifManger = NotificationManagerCompat.from(context)
//        if (PermissionUtils.checkPostNotificationPermission(context)
//        ) {
//            notifManger.notify(NOTIF_ID,notif)
//
//        }

        return Result.success()
    }

    companion object {

        val CHANNEL_ID = "channelID"
        val CHANNEL_NAME = "channelName"
        val NOTIF_ID = 0

        private val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        fun scheduleWorker(context: Context?) {
            val periodicWorkRequest: PeriodicWorkRequest = PeriodicWorkRequest.Builder(
                SyncWorker::class.java,
                15,
                TimeUnit.MINUTES,
                1,
                TimeUnit.MINUTES
            ).setConstraints(constraints).build()
            WorkManager.getInstance(context!!).enqueue(periodicWorkRequest)
        }

//        private fun createNotifChannel() {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                val channel = NotificationChannel("sd", "SS", NotificationManager.IMPORTANCE_DEFAULT).apply {
//                    lightColor = Color.BLUE
//                    enableLights(true)
//                }
//                val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//                manager.createNotificationChannel(channel)
//            }
//        }

    }
}
