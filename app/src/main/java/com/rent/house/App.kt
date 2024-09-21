package com.rent.house


import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var pm: PM

    override fun onCreate() {
        super.onCreate()
        // initialize mob ads
    }

}