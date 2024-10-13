package com.bluetooth.printer.device

import android.graphics.Bitmap
import kotlin.math.ceil

class BTUtils  {
    companion object {
        private const val PRINTER_DPI: Int = 203
        private const val PRINTER_WIDTH_MM: Float = 48f

        private fun initGSv0Command(bytesByLine: Int, bitmapHeight: Int): ByteArray {
            val xH = bytesByLine / 256
            val xL = bytesByLine - (xH * 256)
            val yH = bitmapHeight / 256
            val yL = bitmapHeight - (yH * 256)
            val imageBytes = ByteArray(8 + bytesByLine * bitmapHeight)
            imageBytes[0] = 0x1D
            imageBytes[1] = 0x76
            imageBytes[2] = 0x30
            imageBytes[3] = 0x00
            imageBytes[4] = xL.toByte()
            imageBytes[5] = xH.toByte()
            imageBytes[6] = yL.toByte()
            imageBytes[7] = yH.toByte()
            return imageBytes
        }


        fun bitmapToBytes(bitmap: Bitmap): ByteArray {
            val printingWidthPx: Int
            val p = Math.round(PRINTER_WIDTH_MM * (PRINTER_DPI.toFloat()) / 25.4f)
            printingWidthPx = p + (p % 8)

            var isSizeEdit = false
            var bitmapWidth = bitmap.width
            var bitmapHeight = bitmap.height

            if (bitmapWidth > printingWidthPx) {
                bitmapHeight =
                    Math.round((bitmapHeight.toFloat()) * (printingWidthPx.toFloat()) / (bitmapWidth.toFloat()))
                bitmapWidth = printingWidthPx
                isSizeEdit = true
            }
            var bitmap1 = bitmap
            if (isSizeEdit) {
                bitmap1 = Bitmap.createScaledBitmap(bitmap, bitmapWidth, bitmapHeight, true)
            }

            return bitmapToBytes1(bitmap1)
        }

        private fun bitmapToBytes1(bitmap: Bitmap): ByteArray {
            val bitmapWidth = bitmap.width
            val bitmapHeight = bitmap.height
            val bytesByLine =
                ceil(((bitmapWidth.toFloat()) / 8f).toDouble()).toInt()

            val imageBytes: ByteArray =
                initGSv0Command(
                    bytesByLine,
                    bitmapHeight
                )

            var i = 8
            var greyscaleCoefficientInit = 0
            val gradientStep = 6

            val colorLevelStep = 765.0 / (15 * gradientStep + gradientStep - 1)

            for (posY in 0 until bitmapHeight) {
                var greyscaleCoefficient = greyscaleCoefficientInit
                val greyscaleLine = posY % gradientStep
                var j = 0
                while (j < bitmapWidth) {
                    var b = 0
                    for (k in 0..7) {
                        val posX = j + k
                        if (posX < bitmapWidth) {
                            val color = bitmap.getPixel(posX, posY)
                            val red = (color shr 16) and 255
                            val green =
                                (color shr 8) and 255
                            val blue = color and 255

                            if ((red + green + blue) < ((greyscaleCoefficient * gradientStep + greyscaleLine)
                                        * colorLevelStep)
                            ) {
                                b = b or (1 shl (7 - k))
                            }

                            greyscaleCoefficient += 5
                            if (greyscaleCoefficient > 15) {
                                greyscaleCoefficient -= 16
                            }
                        }
                    }
                    imageBytes[i++] = b.toByte()
                    j += 8
                }
                greyscaleCoefficientInit += 2
                if (greyscaleCoefficientInit > 15) {
                    greyscaleCoefficientInit = 0
                }
            }
            return imageBytes
        }

    }
}