package com.book.gaadi.utils


import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage


class FBS {
    companion object {
        fun getReference(gId: String): StorageReference {
            val storageRef = Firebase.storage.reference;
            return storageRef.child("Gaadis/$gId")
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
    }
}