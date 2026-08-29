package com.bank.userapp

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bank.userapp.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val root = findViewById<RelativeLayout>(R.id.loginRoot)
        val animDrawable = root.background as AnimationDrawable
        animDrawable.setEnterFadeDuration(2000)
        animDrawable.setExitFadeDuration(2000)
        animDrawable.start()

        val etAccount = findViewById<EditText>(R.id.etAccount)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val session = UserSession(this)

        btnLogin.setOnClickListener {
            val account = etAccount.text.toString()
            val password = etPassword.text.toString()

            if (account.isNotEmpty() && password.isNotEmpty()) {
                session.setLoggedIn(true)
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
