package com.bluetooth.printer.device

import android.bluetooth.BluetoothSocket
import android.graphics.Bitmap
import java.io.IOException

class BluetoothPrinter {

    companion object {
        public fun sendBitMapData(socket: BluetoothSocket, data: Bitmap) {
            try {
                val outputStream = socket.outputStream
                val bytes: ByteArray = BTUtils.bitmapToBytes(data)
                outputStream.write(byteArrayOf(0x1b, 'a'.code.toByte(), 0x01))
                outputStream.write(bytes)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun receiveData(socket: BluetoothSocket): String {
            val buffer = ByteArray(1024)
            var bytes: Int
            return try {
                val inputStream = socket.inputStream
                bytes = inputStream.read(buffer)
                String(buffer, 0, bytes)
            } catch (e: IOException) {
                e.printStackTrace()
                ""
            }
        }
    }
}