package com.book.gaadi.utils

class Constants {
    companion object {
        const val BASE_URL = "https://server0308.azurewebsites.net/"

        const val ABOUT_US_URL = "https://nagraj0308.github.io/book_gaadi_aboutus/"
        const val PNP_URL = "https://nagraj0308.github.io/book_gaadi_pnp/"
        const val PLAYSTORE_URL = "https://play.google.com/store/apps/details?id=com.book.gaadi"
        const val APK_SHARE_MSG =
            "शादी, टूर और किराए पर वाहन को बुक करना या अपने वाहन को इन सभी काम के लिए पंजीकृत करना ।  Book Gaadi ऐप इंस्टॉल करें। $PLAYSTORE_URL "

        val vehicleTypes = listOf("Car", "Auto", "Bike", "Bus", "Pickup", "Truck", "Others")
    }
}
