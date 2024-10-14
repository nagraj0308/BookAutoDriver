package com.bluetooth.printer


import android.app.Application
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var pm: PM

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this@App) {
        }
        // initialize mob ads
    }

}