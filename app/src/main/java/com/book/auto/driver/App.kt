package com.book.auto.driver

import android.app.Application
import com.book.auto.driver.utils.SyncWorker
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application(){
    override fun onCreate() {
        super.onCreate()
        SyncWorker.scheduleWorker(this)
    }
}