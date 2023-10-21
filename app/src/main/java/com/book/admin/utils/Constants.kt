package com.book.admin.utils

import com.book.admin.data.remote.reqres.VS
import java.text.SimpleDateFormat
import java.util.Date

class Constants {
    companion object {
        const val BASE_URL = "http://43.204.42.94:5002/"

        val vss = listOf(VS("Pending", ""), VS("Pending", ""), VS("Pending", ""))


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
