package com.book.auto.utils

class Constants {
    //#557a2a
    //rgb(85, 122, 42)
    companion object {
        const val BASE_URL = "https://server0308.azurewebsites.net/"
        const val ABOUT_US_URL = "https://nagraj0308.github.io/book_auto_aboutus/"
        const val PNP_URL = "https://nagraj0308.github.io/book_auto_pnp/"
        const val PLAYSTORE_URL = "https://play.google.com/store/apps/details?id=com.book.auto"
        const val PLAYSTORE_URL_DRIVER =
            "https://play.google.com/store/apps/details?id=com.book.auto.driver"
        const val APK_SHARE_MSG =
            "Hey!! I am using Book Auto app for search nearby vehicles, you can also try., link:- $PLAYSTORE_URL Thanks!!"
        val autoTypes = listOf("4 Wheeler", "3 Wheeler", "2 Wheeler", "Other")
    }
}
