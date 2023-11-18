package com.rent.house.utils


import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.view.View
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage


class FBS {
    companion object {
        fun getReference(gId: String): StorageReference {
            val storageRef = Firebase.storage.reference;
            return storageRef.child("RentHouse/$gId")
        }
    }
}

class Utils {
    companion object {
        fun screenShot(view: View?): Bitmap? {
            if (view != null) {
                view.isDrawingCacheEnabled = true
                val bitmap = Bitmap.createBitmap(
                    view.measuredWidth, view.measuredHeight,
                    Bitmap.Config.RGB_565
                )
                view.isDrawingCacheEnabled = false
                val canvas = Canvas(bitmap)
                view.draw(canvas)
                return bitmap
            }
            return null
        }

        fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
            val results = FloatArray(1)
            Location.distanceBetween(lat1, lon1, lat2, lon2, results)
            return String.format("%.2f", results[0] / 1000).toDouble()
        }
    }
}