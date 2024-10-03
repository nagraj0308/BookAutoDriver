package com.rent.house


import android.app.Application
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
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