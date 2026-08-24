package com.bank.userapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PaymentActivity : AppCompatActivity() {

    private lateinit var tvBalance: TextView
    private lateinit var session: UserSession

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        session = UserSession(this)
        tvBalance = findViewById(R.id.tvBalance)
        
        updateBalanceDisplay()

        findViewById<LinearLayout>(R.id.btnToContact).setOnClickListener {
            val intent = Intent(this, TransferActivity::class.java)
            startActivity(intent)
        }

        findViewById<FloatingActionButton>(R.id.fabScan).setOnClickListener {
            val intent = Intent(this, TransferActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnOpenPortal).setOnClickListener {
            val intent = Intent(this, WebPortalActivity::class.java)
            intent.putExtra("url", getString(R.string.app_base_url))
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        updateBalanceDisplay()
    }

    private fun updateBalanceDisplay() {
        tvBalance.text = "₹ ${session.getBalance()}"
    }
}
