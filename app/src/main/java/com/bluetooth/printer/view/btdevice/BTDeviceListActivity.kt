package com.bluetooth.printer.view.btdevice

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bluetooth.printer.PM
import com.bluetooth.printer.R
import com.bluetooth.printer.data.BTDevice
import com.bluetooth.printer.databinding.ActivityBtDeviceListBinding
import com.bluetooth.printer.view.base.BaseActivity
import com.bluetooth.printer.view.utils.PermissionUtils
import com.bluetooth.printer.view.utils.RequestCode
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class BTDeviceListActivity : BaseActivity() {
    @Inject
    lateinit var pm: PM

    private lateinit var binding: ActivityBtDeviceListBinding


    companion object {
        fun start(activity: Activity) {
            val intent = Intent(activity, BTDeviceListActivity::class.java);
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBtDeviceListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.let {
            it.title = "Select Bluetooth Device"
            it.setDisplayHomeAsUpEnabled(true);
            it.setDisplayShowHomeEnabled(true);
        }
        binding.rcvPairedDevices.layoutManager = LinearLayoutManager(this);
        if (PermissionUtils.checkBTDeviceConnectPermission(this)) {
            showBTDevices()
        } else {
            PermissionUtils.requestBTDeviceConnectPermission(this);
        }

    }

    private fun showBTDevices() {
        val pairedDevices = getBTDevices()
        binding.rcvPairedDevices.adapter =
            BTDeviceListAdapter(pairedDevices, onClickListener = { _, item ->
                run {
                    pm.btDeviceName = item.name
                    pm.btDeviceAddress = item.address
                }
            })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RequestCode.BT_DEVICE && PermissionUtils.checkBTDeviceConnectPermission(this)) {
            showBTDevices()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getBTDevices(): ArrayList<BTDevice> {
        val btDevices: ArrayList<BTDevice> = ArrayList()
        val bluetoothManager = getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        val pairedDevices: Set<BluetoothDevice> = bluetoothManager.adapter.getBondedDevices()
        if (pairedDevices.isNotEmpty()) {
            for (device in pairedDevices) {
                btDevices.add(BTDevice(device.name, device.address, R.drawable.ic_home))
            }
        } else {
            showToast("No paired bluetooth printer, Please connect with a bluetooth printer!")
        }
        return btDevices
    }

}