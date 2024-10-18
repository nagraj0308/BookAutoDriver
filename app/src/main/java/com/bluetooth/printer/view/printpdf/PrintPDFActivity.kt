package com.bluetooth.printer.view.printpdf

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.util.Log
import com.bluetooth.printer.PM
import com.bluetooth.printer.data.PrintType
import com.bluetooth.printer.databinding.ActivityPrintPdfBinding
import com.bluetooth.printer.device.BluetoothConnection
import com.bluetooth.printer.device.BluetoothPrinter
import com.bluetooth.printer.view.base.BaseActivity
import com.bluetooth.printer.view.btdevice.BTDeviceListActivity
import com.bluetooth.printer.view.utils.PermissionUtils
import com.bluetooth.printer.view.utils.RequestCodeIntent
import com.bluetooth.printer.view.utils.RequestCodePermission
import com.bluetooth.printer.view.utils.Utils
import com.google.android.gms.ads.AdRequest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@AndroidEntryPoint
class PrintPDFActivity : BaseActivity() {
    @Inject
    lateinit var pm: PM

    private var printType: PrintType? = null

    private lateinit var binding: ActivityPrintPdfBinding

    private lateinit var selectedFile:Uri


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
            printer?.let { it ->
                run {
                    if (it.isConnected) {
                        if (selectedFile != null) {
                            selectedFile.path?.let {

                            }
                        }else{
                            showToast("File not selected!")
                        }

                        Utils.screenShot(binding.ivSelectPdf)
                            ?.let { it2 -> BluetoothPrinter.sendBitMapData(it1, it2) }
                    } else {
                        showToast("Printer not connected")
                        BTDeviceListActivity.start(this)
                    }
                }
            }

            BluetoothPrinter.sendBitMapData(it, it2)
        }
        binding.tvFileName.text = "File Name : ";
        binding.ivSelectPdf.setOnClickListener {
            if (PermissionUtils.checkReadStoragePermission(this)) {
                Utils.openPDFChooser(this)
            } else {
                PermissionUtils.requestReadStoragePermission(this)
            }
        }
    }

    private fun connectBTDevice() {
        if (PermissionUtils.checkBTDeviceConnectPermission(this)) {
            if (BluetoothAdapter.checkBluetoothAddress(pm.btDeviceAddress)) {
                CoroutineScope(Dispatchers.IO).launch {
                    if (printer == null || !printer!!.isConnected) {
                        printer = BluetoothConnection().connectToDevice(pm.btDeviceAddress)
                        withContext(Dispatchers.Main) {
                            if (printer == null || !printer!!.isConnected) {
                                showToast("Failed to connect to printer")
                            } else {
                                showToast("Connected to printer")
                            }
                        }
                    } else {
                        showToast("Connected to printer")
                    }
                }
            } else {
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

        if (requestCode == RequestCodePermission.READ_STORAGE && PermissionUtils.checkReadStoragePermission(
                this
            )
        ) {
            Utils.openPDFChooser(this)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RequestCodeIntent.BT_DEVICE_LIST && resultCode == Activity.RESULT_OK) {
            connectBTDevice()
        }

        if (requestCode == RequestCodeIntent.READ_CONTENT && resultCode == Activity.RESULT_OK) {

            if (data==null){
                Log.v("NAGRAJ", "A")
                return
            }
            if (data.data==null){
                selectedFile= data.data!!
                Log.v("NAGRAJ", "B")
                return
            }

            if (data.data!!.path==null){
                Log.v("NAGRAJ", "C")
                return
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val adRequest: AdRequest = AdRequest.Builder().build()
        binding.bannerAd.loadAd(adRequest)
    }
}

