package com.bluetooth.printer.device

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import java.io.IOException
import java.util.UUID

class BluetoothConnection {
    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

    // This is a random UUID; replace with the UUID used by your Bluetooth device.
    private val MY_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    fun connectToDevice(deviceAddress: String, activity: Activity): BluetoothSocket? {
        val device: BluetoothDevice? = bluetoothAdapter?.getRemoteDevice(deviceAddress)
        var socket: BluetoothSocket? = null

        try {

            if (ActivityCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            socket = device?.createRfcommSocketToServiceRecord(MY_UUID)
            socket?.connect()
            println("Connection successful!")
        } catch (e: IOException) {
            e.printStackTrace()
            try {
                socket?.close()
            } catch (closeException: IOException) {
                closeException.printStackTrace()
            }
        }

        return socket
    }
}