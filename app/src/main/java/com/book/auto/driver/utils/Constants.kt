package com.book.auto.driver.utils

class Constants {
    companion object {
        //        #557a2a
//        rgb(85, 122, 42)
        const val BASE_URL = "https://server0308.azurewebsites.net/"
        const val ABOUT_US_URL = "https://nagraj0308.github.io/book_auto_driver_aboutus/"
        const val PNP_URL = "https://nagraj0308.github.io/book_auto_driver_pnp/"
        const val PLAYSTORE_URL =
            "https://play.google.com/store/apps/details?id=com.book.auto.driver"
        const val APK_SHARE_MSG =
            "Hey!! I am using Book Auto Driver app for adding our vehicles, you can also try., link:- $PLAYSTORE_URL Thanks!!"
        const val IMG_HBW = 0.66
        const val SYNC_TIME = 10//seconds

        val gaadiTypes = listOf("4 Wheeler", "3 Wheeler", "2 Wheeler", "Other")
    }
}
