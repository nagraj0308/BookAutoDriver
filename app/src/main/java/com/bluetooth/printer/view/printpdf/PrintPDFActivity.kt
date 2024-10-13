package com.bluetooth.printer.view.printpdf

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.bluetooth.printer.PM
import com.bluetooth.printer.data.PrintType
import com.bluetooth.printer.databinding.ActivityPrintPdfBinding
import com.bluetooth.printer.device.BluetoothConnection
import com.bluetooth.printer.view.base.BaseActivity
import com.bluetooth.printer.view.btdevice.BTDeviceListActivity
import com.bluetooth.printer.view.utils.PermissionUtils
import com.bluetooth.printer.view.utils.RequestCodeIntent
import com.bluetooth.printer.view.utils.RequestCodePermission
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject


@AndroidEntryPoint
class PrintPDFActivity : BaseActivity() {
    @Inject
    lateinit var pm: PM

    private var printType: PrintType? = null
    private var printer: BluetoothSocket? = null

    private lateinit var binding: ActivityPrintPdfBinding


    companion object {
        fun start(activity: Activity, printType: PrintType) {
            val intent = Intent(activity, PrintPDFActivity::class.java);
            intent.putExtra("item", printType)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrintPdfBinding.inflate(layoutInflater)
        setContentView(binding.root)
        printType = intent.getSerializableExtra("item") as PrintType?
        printType?.let {
            supportActionBar?.title = it.name
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        connectBTDevice()

        binding.btnPrint.setOnClickListener {
            printer?.let { it1 ->
                run {
                    if (it1.isConnected) {
                        sendData(it1, " अंश नोबादा अंश नोबादा  अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा")
                    } else {
                        showToast("Printer not connected")
                        BTDeviceListActivity.start(this)
                    }
                }
            }
        }
    }

    private fun connectBTDevice() {
        if (PermissionUtils.checkBTDeviceConnectPermission(this)) {
            if(BluetoothAdapter.checkBluetoothAddress(pm.btDeviceAddress)){
                CoroutineScope(Dispatchers.IO).launch {
                    printer = BluetoothConnection().connectToDevice(pm.btDeviceAddress)
                    withContext(Dispatchers.Main) {
                        if (printer == null || !printer!!.isConnected) {
                            showToast("Failed to connect to printer")
                        }else{
                            showToast("Connected to printer")
                        }
                    }
                }
            }else{
                BTDeviceListActivity.start(this)
            }
        } else {
            PermissionUtils.requestBTDeviceConnectPermission(this);
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RequestCodePermission.BT_DEVICE_CONNECT && PermissionUtils.checkBTDeviceConnectPermission(
                this
            )
        ) {
            connectBTDevice()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun sendData(socket: BluetoothSocket, data: String) {
        try {
            val outputStream = socket.outputStream
            outputStream.write(data.toByteArray())
        } catch (e: IOException) {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RequestCodeIntent.BT_DEVICE_LIST && resultCode == Activity.RESULT_OK){
            connectBTDevice()
        }

        if (requestCode == RequestCodeIntent.READ_CONTENT && resultCode == Activity.RESULT_OK) {
            val pdfUri: Uri? = data?.data
            pdfUri?.let {
                  ///
             }
        }
    }


}