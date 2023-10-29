package com.book.auto.driver.presentation.synclocation

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit


class Sync15MinWorker(private var context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        Log.v("NAGRAJ", "AB");
        return Result.success()
    }

    companion object {
        private val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        fun scheduleWorker(context: Context?) {
            val periodicWorkRequest: PeriodicWorkRequest = PeriodicWorkRequest.Builder(
                Sync15MinWorker::class.java,
                15,
                TimeUnit.MINUTES,
                1,
                TimeUnit.MINUTES
            ).setConstraints(constraints).build()
            WorkManager.getInstance(context!!).enqueue(periodicWorkRequest)
        }
    }
}
