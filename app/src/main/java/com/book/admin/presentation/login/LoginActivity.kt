package com.book.admin.presentation.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.book.admin.PM
import com.book.admin.databinding.ActivityLoginBinding
import com.book.admin.presentation.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var pm: PM

    private lateinit var binding: ActivityLoginBinding


    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        runBlocking {
            if (pm.isLoggedIn) {
                gotoHomePage()
            }
        }


        binding.btnLogin.setOnClickListener {

            runBlocking {
                GlobalScope.launch(
                    Dispatchers.IO
                ) {
                    val pass = binding.tilPassword.editText!!.text.trim().toString();
                    if (pass.length >= 6) {
                        withContext(Dispatchers.Main) {
                            pm.isLoggedIn = true
                            pm.password = pass
                            gotoHomePage()
                        }

                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Pass should be min 6 digit!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }


    private fun gotoHomePage() {
        finish()
        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
    }
}