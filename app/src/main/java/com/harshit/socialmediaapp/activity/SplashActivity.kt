package com.harshit.socialmediaapp.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.harshit.socialmediaapp.R


class SplashActivity : AppCompatActivity() {

    lateinit var txtSplashName: TextView
    private var splashLoaded = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        txtSplashName = findViewById(R.id.txtSplashName)
        val bottomTOTop: Animation = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top_splash)
        txtSplashName.startAnimation(bottomTOTop)

        if (!splashLoaded) {
            setContentView(R.layout.activity_splash)
            val secondsDelayed = 1
            Handler().postDelayed(Runnable {
                startActivity(Intent(this@SplashActivity, SignInActivity::class.java))
                finish()
            }, (secondsDelayed * 500).toLong())
            splashLoaded = true
        } else {
            val goToMainActivity = Intent(this@SplashActivity, SignInActivity::class.java)
            goToMainActivity.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(goToMainActivity)
            finish()
        }
    }
}