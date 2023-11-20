package com.book.auto.presentation.home

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.Button
import androidx.activity.viewModels
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.book.auto.BuildConfig
import com.book.auto.R
import com.book.auto.databinding.ActivityHomeBinding
import com.book.auto.databinding.NavHeaderMainBinding
import com.book.auto.presentation.base.BaseActivity
import com.book.auto.utils.Constants
import com.book.auto.utils.PermissionUtils
import com.book.auto.utils.RequestCode
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.material.navigation.NavigationView
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


@AndroidEntryPoint
class HomeActivity : BaseActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding
    private lateinit var headerBinding: NavHeaderMainBinding
    private var remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        MapsInitializer.initialize(this, MapsInitializer.Renderer.LATEST) {}
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        headerBinding = NavHeaderMainBinding.bind(binding.navView.getHeaderView(0))


        setSupportActionBar(binding.appBarMain.toolbar)


        headerBinding.tvName.text = ""
        headerBinding.tvGmail.text = ""

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_about_us, R.id.nav_pnp
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.menu.findItem(R.id.nav_share).setOnMenuItemClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, Constants.APK_SHARE_MSG)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, "Share app")
            startActivity(shareIntent)
            true
        }
        navView.menu.findItem(R.id.nav_rate).setOnMenuItemClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW, Uri.parse(Constants.PLAYSTORE_URL)
                )
            )
            true
        }
        navView.menu.findItem(R.id.nav_install_driver_app).setOnMenuItemClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW, Uri.parse(Constants.PLAYSTORE_URL_DRIVER)
                )
            )
            true
        }
    }


    override fun onStart() {
        super.onStart()
        updateLocation()
        remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 300
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        remoteConfig.fetchAndActivate().addOnCompleteListener(
            this
        ) { task ->
            if (task.isSuccessful) {
                val minVersion = remoteConfig.getLong("min_version")
                if (BuildConfig.VERSION_CODE < minVersion) {
                    showDialog()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RequestCode.LOCATION && PermissionUtils.checkLocationAccessPermission(
                this
            )
        ) {
            updateLocation()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_dailog)
        val yesBtn = dialog.findViewById(R.id.btn_update) as Button
        yesBtn.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW, Uri.parse(Constants.PLAYSTORE_URL)
                )
            )
        }
        dialog.show()
    }


    fun updateLocation() {
        if (PermissionUtils.checkLocationEnabled(this)) {
            if (PermissionUtils.checkLocationAccessPermission(this)) {
                lifecycleScope.launch(Dispatchers.IO) {
                    val result = fusedLocationClient.getCurrentLocation(
                        Priority.PRIORITY_HIGH_ACCURACY,
                        CancellationTokenSource().token,
                    ).await()
                    result?.let {
                        viewModel.onLocationUpdated(it.latitude, it.longitude)
                    }
                }
            } else {
                PermissionUtils.requestLocationAccessPermission(this)
            }
        } else {
            PermissionUtils.requestLocationEnableRequest(this)
        }
    }

}