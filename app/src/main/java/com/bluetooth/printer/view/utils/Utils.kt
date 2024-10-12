package com.bluetooth.printer.view.utils


import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.pdf.PdfRenderer
import android.location.Location
import android.os.ParcelFileDescriptor
import android.view.View
import java.io.File


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

        fun renderPdfPage(filePath: String, pageIndex: Int): Bitmap? {
            val pdfFile = File(filePath)
            var bitmap: Bitmap? = null

            try {
                val fileDescriptor =
                    ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY)
                val renderer = PdfRenderer(fileDescriptor)

                val page = renderer.openPage(pageIndex)
                val width = page.width
                val height = page.height

                // Create a bitmap large enough to hold the PDF page
                bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

                // Render the PDF page to the bitmap
                page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

                page.close()
                renderer.close()

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return bitmap
        }

    }
}