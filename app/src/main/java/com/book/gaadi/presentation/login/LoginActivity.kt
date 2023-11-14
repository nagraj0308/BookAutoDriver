package com.book.gaadi.presentation.login

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.core.app.ActivityCompat
import com.book.gaadi.PM
import com.book.gaadi.R
import com.book.gaadi.databinding.ActivityLoginBinding
import com.book.gaadi.presentation.base.BaseActivity
import com.book.gaadi.presentation.home.HomeActivity
import com.book.gaadi.utils.RequestCode
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    @Inject
    lateinit var pm: PM

    private lateinit var binding: ActivityLoginBinding

    private lateinit var mGoogleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (pm.isLoggedIn) {
            login()
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
        startActivityForResult(signInIntent, RequestCode.GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RequestCode.GOOGLE_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }


    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                runBlocking {
                    if (account.email == null) {
                        showToast(getString(R.string.there_is_some_error))
                    } else {
                        pm.isLoggedIn = true
                        pm.gmail = account.email.toString()
                        pm.name = account.displayName.toString()
                        login()
                    }
                }
            }
        } catch (e: ApiException) {
            showToast(e.printStackTrace().toString())
            e.printStackTrace()
        }
    }


    private fun login() {
        setLangCode(pm.lang)
        finish()
        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
    }

    override fun onStart() {
        super.onStart()
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ), 0
        )
    }

}