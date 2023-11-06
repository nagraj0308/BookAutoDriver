package com.book.gaadi.presentation.base

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


open class BaseActivity : AppCompatActivity() {
    fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show()
    }
}