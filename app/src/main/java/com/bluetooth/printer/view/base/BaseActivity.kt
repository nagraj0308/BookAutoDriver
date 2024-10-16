package com.bluetooth.printer.view.base

import android.bluetooth.BluetoothSocket
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


open class BaseActivity : AppCompatActivity() {
    var printer: BluetoothSocket? = null
    var printer2: BluetoothSocket? = null

    fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show()
    }

}