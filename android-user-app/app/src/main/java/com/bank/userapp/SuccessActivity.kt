package com.bank.userapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)

        val amount = intent.getDoubleExtra("amount", 0.0)
        val session = UserSession(this)

        findViewById<TextView>(R.id.tvSuccessAmount).text = "₹ $amount"
        findViewById<TextView>(R.id.tvSuccessBalance).text = "New Balance: ₹ ${session.getBalance()}"

        findViewById<Button>(R.id.btnDone).setOnClickListener {
            finish()
        }
    }
}
