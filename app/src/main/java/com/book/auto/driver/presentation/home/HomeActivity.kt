package com.book.auto.driver.presentation.home

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.book.auto.driver.BuildConfig
import com.book.auto.driver.R
import com.book.auto.driver.databinding.ActivityHomeBinding
import com.book.auto.driver.databinding.NavHeaderMainBinding
import com.book.auto.driver.presentation.login.LoginActivity
import com.book.auto.driver.utils.Constants
import com.google.android.material.navigation.NavigationView
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding
    private lateinit var headerBinding: NavHeaderMainBinding
    private var remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        headerBinding = NavHeaderMainBinding.bind(binding.navView.getHeaderView(0))

        viewModel.initDataStore(this)

        setSupportActionBar(binding.appBarMain.toolbar)

        headerBinding.tvName.text = viewModel.readState.value?.name ?: ""
        headerBinding.tvGmail.text = viewModel.readState.value?.email ?: ""

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_about_us,
                R.id.nav_pnp
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
                    Intent.ACTION_VIEW,
                    Uri.parse(Constants.PLAYSTORE_URL)
                )
            )
            true
        }
        navView.menu.findItem(R.id.nav_exit).setOnMenuItemClickListener {
            viewModel.logout()
            finish()
            startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
            true
        }
    }


    override fun onStart() {
        super.onStart()
        viewModel.updateLocation(this)
        remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 300
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(
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
                    Intent.ACTION_VIEW,
                    Uri.parse(Constants.PLAYSTORE_URL)
                )
            )
        }
        dialog.show()
    }

    private fun showToast(msg: String) {
        Toast.makeText(
            this@HomeActivity, msg, Toast.LENGTH_LONG
        ).show()
    }
}