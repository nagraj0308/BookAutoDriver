package com.bluetooth.printer.view.btdevice

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothClass
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
import com.bluetooth.printer.view.utils.RequestCodeIntent
import com.bluetooth.printer.view.utils.RequestCodePermission
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
            activity.startActivityForResult(intent,RequestCodeIntent.BT_DEVICE_LIST)
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
                    finish()
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
        if (requestCode == RequestCodePermission.BT_DEVICE_CONNECT && PermissionUtils.checkBTDeviceConnectPermission(this)) {
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
                btDevices.add(BTDevice(device.name, device.address, getDeviceIcon(device.bluetoothClass.majorDeviceClass)))
            }
        } else {
            showToast("No paired bluetooth printer, Please connect with a bluetooth printer!")
        }
        return btDevices
    }

    private fun getDeviceIcon(major: Int):Int{

      return  when (major) {
            BluetoothClass.Device.Major.AUDIO_VIDEO -> R.drawable.ic_audiotrack
            BluetoothClass.Device.Major.COMPUTER -> R.drawable.ic_computer
            BluetoothClass.Device.Major.HEALTH -> R.drawable.ic_health
            BluetoothClass.Device.Major.IMAGING -> R.drawable.ic_print
            BluetoothClass.Device.Major.MISC -> R.drawable.ic_device_unknown
            BluetoothClass.Device.Major.NETWORKING -> R.drawable.ic_info
            BluetoothClass.Device.Major.PERIPHERAL -> R.drawable.ic_home
            BluetoothClass.Device.Major.PHONE -> R.drawable.ic_phone
            BluetoothClass.Device.Major.TOY -> R.drawable.ic_toys
            BluetoothClass.Device.Major.UNCATEGORIZED -> R.drawable.ic_device_unknown
            BluetoothClass.Device.Major.WEARABLE -> R.drawable.ic_headphones
            else -> R.drawable.ic_device_unknown
        }
    }

}