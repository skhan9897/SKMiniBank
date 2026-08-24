package com.bank.userapp

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val layout = findViewById<RelativeLayout>(R.id.splash_layout)
        val animationDrawable = layout.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(2000)
        animationDrawable.start()

        val logoImg = findViewById<ImageView>(R.id.logo_img)
        val rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate)
        logoImg.startAnimation(rotateAnimation)

        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        
        val handler = Handler(Looper.getMainLooper())
        var progressStatus = 0
        
        Handler(Looper.getMainLooper()).postDelayed({
            val session = UserSession(this)
            if (session.isLoggedIn()) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }, 3000)
    }
}
