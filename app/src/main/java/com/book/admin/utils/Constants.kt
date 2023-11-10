package com.book.admin.utils

import com.book.admin.data.remote.reqres.VS
import java.text.SimpleDateFormat
import java.util.Date

class Constants {
    companion object {
        const val BASE_URL = "https://server0308.azurewebsites.net/"
        val vss = listOf(VS("Pending", "U"), VS("Accepted", "A"), VS("Rejected", "R"))
        val autoTypes = listOf("Car", "Auto", "Bike", "Bus")
        fun status(code: String): VS {
            return when (code) {
                "U" -> {
                    vss[0]
                }

                "A" -> {
                    vss[1]
                }

                "R" -> {
                    vss[2]
                }

                else -> {
                    vss[0]
                }
            }
        }
    }
}

class Utils {
    companion object {
        fun convertLongToTime(time: Long): String {
            val date = Date(time)
            val format = SimpleDateFormat("dd MMM, yyyy  HH:mm")
            return format.format(date)
        }
    }

}
