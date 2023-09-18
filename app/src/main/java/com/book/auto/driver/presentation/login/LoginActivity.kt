package com.book.auto.driver.presentation.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.book.auto.driver.R
import com.book.auto.driver.data.DataStore
import com.book.auto.driver.databinding.ActivityLoginBinding
import com.book.auto.driver.presentation.home.HomeActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var dataStore: DataStore

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    val REQ_CODE: Int = 123

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataStore = DataStore(this)
        GlobalScope.launch(
            Dispatchers.IO
        ) {
            dataStore.isLogin.collect {
                if (it) {
                    finish()
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                }
            }
        }

        val animationFadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        binding.ivLogo.startAnimation(animationFadeOut)
        Handler().postDelayed({
            binding.ivLogo.scaleType
        }, 1000)
        binding.btnLogin.setOnClickListener {
            signInGoogle()
        }


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

    }


    private fun signInGoogle() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, REQ_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CODE) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }


    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                runBlocking {
                    GlobalScope.launch(
                        Dispatchers.IO
                    ) {
                        if (account.email == null) {
                            showToast("There is some error!!")
                        } else {
                            dataStore.setLogin(true)
                            dataStore.setEmail(account.email.toString())
                            dataStore.setName(account.displayName.toString())
                        }

                    }
                }
            }
        } catch (e: ApiException) {
            showToast(e.printStackTrace().toString())
            e.printStackTrace()
        }
    }


    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

}