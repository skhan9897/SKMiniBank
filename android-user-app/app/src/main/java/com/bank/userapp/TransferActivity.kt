package com.bank.userapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

class TransferActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }

        val etAmount = findViewById<EditText>(R.id.etAmount)
        val btnPay = findViewById<Button>(R.id.btnPay)
        val session = UserSession(this)

        btnPay.setOnClickListener {
            val amountStr = etAmount.text.toString()
            if (amountStr.isNotEmpty()) {
                val amount = amountStr.toDouble()
                val currentBalance = session.getBalance()

                if (amount <= 0) {
                    Toast.makeText(this, "Enter valid amount", Toast.LENGTH_SHORT).show()
                } else if (amount > currentBalance) {
                    Toast.makeText(this, "Insufficient balance", Toast.LENGTH_SHORT).show()
                } else {
                    session.updateBalance(amount)
                    val intent = Intent(this, SuccessActivity::class.java)
                    intent.putExtra("amount", amount)
                    startActivity(intent)
                    finish()
                }
            } else {
                Toast.makeText(this, "Please enter amount", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
