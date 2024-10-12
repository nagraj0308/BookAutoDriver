package com.bluetooth.printer.device

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import java.io.IOException
import java.util.UUID

class BluetoothConnection {
    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

    // This is a random UUID; replace with the UUID used by your Bluetooth device.
    private val MY_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    @SuppressLint("MissingPermission")
    fun connectToDevice(deviceAddress: String): BluetoothSocket? {
        val device: BluetoothDevice? = bluetoothAdapter?.getRemoteDevice(deviceAddress)
        var socket: BluetoothSocket? = null

        try {

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