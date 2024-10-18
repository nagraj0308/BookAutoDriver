package com.bluetooth.printer.view.printimage

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.bluetooth.printer.PM
import com.bluetooth.printer.data.PrintType
import com.bluetooth.printer.databinding.ActivityPrintImageBinding
import com.bluetooth.printer.device.BluetoothConnection
import com.bluetooth.printer.device.BluetoothPrinter
import com.bluetooth.printer.view.base.BaseActivity
import com.bluetooth.printer.view.btdevice.BTDeviceListActivity
import com.bluetooth.printer.view.utils.PermissionUtils
import com.bluetooth.printer.view.utils.RequestCodeIntent
import com.bluetooth.printer.view.utils.RequestCodePermission
import com.bluetooth.printer.view.utils.Utils
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject


@AndroidEntryPoint
class PrintImageActivity : BaseActivity() {
    @Inject
    lateinit var pm: PM

    private var printType: PrintType? = null

    private lateinit var binding: ActivityPrintImageBinding


    companion object {
        fun start(activity: Activity, printType: PrintType) {
            val intent = Intent(activity, PrintImageActivity::class.java);
            intent.putExtra("item", printType)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrintImageBinding.inflate(layoutInflater)
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
                        Utils.screenShot(binding.ivSelectPdf)
                            ?.let { it2 -> BluetoothPrinter.sendBitMapData(it1, it2) }
//                        sendData(it1, " अंश नोबादा अंश नोबादा  अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा अंश नोबादा")
                    } else {
                        showToast("Printer not connected")
                        BTDeviceListActivity.start(this)
                    }
                }
            }
        }
        binding.tvFileName.text = "File Name : ";
        binding.ivSelectPdf.setOnClickListener {
            if (PermissionUtils.checkReadStoragePermission(this)) {
                startForResult.launch(
                    Intent(
                        Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                )
            } else {
                PermissionUtils.requestReadStoragePermission(this)
            }
        }
    }

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK && null != result.data) {
            val data = result.data
            val selectedImage: Uri = data!!.data!!
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = this.contentResolver.query(
                selectedImage, filePathColumn, null, null, null
            )
            cursor!!.moveToFirst()
            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
            val picturePath = cursor.getString(columnIndex)
            cursor.close()
            Glide.with(this).asBitmap().load(picturePath).into(binding.ivSelectPdf)
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

    private fun sendData(socket: BluetoothSocket, data: String) {
        try {
            val outputStream = socket.outputStream
            outputStream.write(data.toByteArray())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RequestCodeIntent.BT_DEVICE_LIST && resultCode == Activity.RESULT_OK) {
            connectBTDevice()
        }

        if (requestCode == RequestCodeIntent.READ_CONTENT && resultCode == Activity.RESULT_OK) {
            val pdfUri: Uri? = data?.data
            pdfUri?.let {
                it.path?.let { it1 ->
                    Utils.renderPdfPage(it1, 1)?.let { it2 ->
                        printer?.let { it3 -> it2?.let { it4 -> BluetoothPrinter.sendBitMapData(it3, it4) } }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val adRequest: AdRequest = AdRequest.Builder().build()
        binding.bannerAd.loadAd(adRequest)
    }
}

