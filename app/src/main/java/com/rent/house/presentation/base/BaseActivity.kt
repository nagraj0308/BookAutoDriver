package com.rent.house.presentation.base

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale


open class BaseActivity : AppCompatActivity() {
    fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show()
    }

    protected open fun setLangCode(code: String?) {
        val locale = Locale(code)
        val config = baseContext.resources.configuration
        config.locale = locale
        baseContext.resources
            .updateConfiguration(config, baseContext.resources.displayMetrics)
    }
}