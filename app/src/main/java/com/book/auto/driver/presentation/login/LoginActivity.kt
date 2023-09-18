package com.book.auto.driver.presentation.login

import android.graphics.drawable.Animatable
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.book.auto.driver.R
import com.book.auto.driver.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animationFadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        binding.ivLogo.startAnimation(animationFadeOut)
        Handler().postDelayed({
            binding.ivLogo.scaleType
        }, 1000)
    }

}