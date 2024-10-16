package com.bluetooth.printer.device

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import java.util.UUID

class BluetoothConnection(private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()) {

    @SuppressLint("MissingPermission")
    fun connectToDevice(deviceAddress: String): BluetoothSocket? {
        val device: BluetoothDevice? = bluetoothAdapter?.getRemoteDevice(deviceAddress)
        var socket: BluetoothSocket? = null

        try {
            socket = device?.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"))//serial; communication uuid
            socket?.connect()
        } catch (e: Exception) {
            e.printStackTrace()
            try {
                socket?.close()
            } catch (closeException: Exception) {
                closeException.printStackTrace()
            }
        }

        return socket
    }
}