package com.book.auto.driver.utils

class Constants {
    companion object {
        const val BASE_URL = "https://bookgaadiserver.onrender.com/"
        const val ABOUT_US_URL = "https://nagraj0308.github.io/book_gaadi_aboutus/"
        const val PNP_URL = "https://nagraj0308.github.io/book_gaadi_pnp/"
        const val PLAYSTORE_URL = "https://play.google.com/store/apps/details?id=com.book.auto.driver"
        const val APK_SHARE_MSG =
            "Hey!! I am using Book Gaadi app for search nearby vehicles, you can also try., link:- $PLAYSTORE_URL Thanks!!"
        const val IMG_WBH = 1.5

        val gaadiTypes = listOf("4 Wheeler", "3 Wheeler", "2 Wheeler", "Other")
    }
}


class RequestCode {
    companion object {
        const val LOCATION = 10
    }
}