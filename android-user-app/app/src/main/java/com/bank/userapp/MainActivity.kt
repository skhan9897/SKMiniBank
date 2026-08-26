package com.bank.userapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.card.MaterialCardView
import com.google.android.material.navigation.NavigationView
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var session: UserSession
    private lateinit var tvBalance: TextView
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        session = UserSession(this)
        
        drawerLayout = findViewById(R.id.drawerLayout)
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        tvBalance = findViewById(R.id.tvBalance)

        toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        val navView = findViewById<NavigationView>(R.id.navigationView)
        navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_logout -> {
                    session.setLoggedIn(false)
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
                R.id.nav_transfer -> {
                    startActivity(Intent(this, TransferActivity::class.java))
                }
                R.id.nav_home -> {
                    openWebPortal()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        // Native Card click to transfer
        findViewById<MaterialCardView>(R.id.btnPay).setOnClickListener {
            startActivity(Intent(this, TransferActivity::class.java))
        }

        // History Card opens the Bank Portal (Old Flow Integration)
        findViewById<MaterialCardView>(R.id.btnHistory).setOnClickListener {
            openWebPortal()
        }

        // Recharge opens the Bank Portal
        findViewById<LinearLayout>(R.id.btnRecharge).setOnClickListener {
            openWebPortal()
        }

        // More opens the Bank Portal
        findViewById<LinearLayout>(R.id.btnMore).setOnClickListener {
            openWebPortal()
        }

        updateBalanceDisplay()
    }

    private fun openWebPortal() {
        val intent = Intent(this, WebPortalActivity::class.java)
        intent.putExtra("url", getString(R.string.app_base_url))
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        updateBalanceDisplay()
    }

    private fun updateBalanceDisplay() {
        tvBalance.text = String.format(Locale.getDefault(), "₹ %.2f", session.getBalance())
    }
}
