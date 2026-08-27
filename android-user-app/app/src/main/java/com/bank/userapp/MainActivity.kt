package com.bank.userapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var session: UserSession
    private lateinit var tvBalance: TextView
    private lateinit var tvKycStatus: TextView
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        session = UserSession(this)
        
        drawerLayout = findViewById(R.id.drawerLayout)
        val toolbar = findViewById<View>(R.id.toolbar)
        tvBalance = findViewById(R.id.tvBalance)
        tvKycStatus = findViewById(R.id.tvKycStatus)

        toolbar?.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        val navView = findViewById<NavigationView>(R.id.navigationView)
        navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_logout -> {
                    logout()
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

        // Navigation Links Handlers
        findViewById<View>(R.id.nav_link_dashboard)?.setOnClickListener { openWebPortal() }
        findViewById<View>(R.id.nav_link_service)?.setOnClickListener { openWebPortal() }
        findViewById<View>(R.id.nav_link_atm)?.setOnClickListener { 
            val intent = Intent(this, WebPortalActivity::class.java)
            intent.putExtra("url", "https://skminibank.onrender.com/admin/atm-card-list.jsp")
            startActivity(intent)
        }
        findViewById<View>(R.id.nav_link_cheque)?.setOnClickListener { 
            val intent = Intent(this, WebPortalActivity::class.java)
            intent.putExtra("url", "https://skminibank.onrender.com/admin/cheque-book-list.jsp")
            startActivity(intent)
        }
        findViewById<View>(R.id.nav_link_net)?.setOnClickListener { 
            val intent = Intent(this, WebPortalActivity::class.java)
            intent.putExtra("url", "https://skminibank.onrender.com/admin/internet-banking-list.jsp")
            startActivity(intent)
        }
        findViewById<View>(R.id.nav_link_mobile)?.setOnClickListener { 
            val intent = Intent(this, WebPortalActivity::class.java)
            intent.putExtra("url", "https://skminibank.onrender.com/admin/mobile-banking.jsp")
            startActivity(intent)
        }
        findViewById<View>(R.id.nav_link_loan_req)?.setOnClickListener { 
            val intent = Intent(this, WebPortalActivity::class.java)
            intent.putExtra("url", "https://skminibank.onrender.com/admin/loan-dashboard.jsp")
            startActivity(intent)
        }
        findViewById<View>(R.id.nav_link_all_req)?.setOnClickListener { 
            val intent = Intent(this, WebPortalActivity::class.java)
            intent.putExtra("url", "https://skminibank.onrender.com/admin/all-service-requests.jsp")
            startActivity(intent)
        }
        findViewById<View>(R.id.nav_link_customers)?.setOnClickListener { 
            val intent = Intent(this, WebPortalActivity::class.java)
            intent.putExtra("url", "https://skminibank.onrender.com/admin/customer-list.jsp")
            startActivity(intent)
        }
        findViewById<View>(R.id.nav_link_add_cust)?.setOnClickListener { 
            val intent = Intent(this, WebPortalActivity::class.java)
            intent.putExtra("url", "https://skminibank.onrender.com/admin/open-account.jsp")
            startActivity(intent)
        }
        findViewById<View>(R.id.nav_link_open_acc)?.setOnClickListener { 
            val intent = Intent(this, WebPortalActivity::class.java)
            intent.putExtra("url", "https://skminibank.onrender.com/admin/open-account.jsp")
            startActivity(intent)
        }
        findViewById<View>(R.id.nav_link_deposit)?.setOnClickListener { 
            val intent = Intent(this, WebPortalActivity::class.java)
            intent.putExtra("url", "https://skminibank.onrender.com/admin/deposit.jsp")
            startActivity(intent)
        }
        findViewById<View>(R.id.nav_link_withdraw)?.setOnClickListener { 
            val intent = Intent(this, WebPortalActivity::class.java)
            intent.putExtra("url", "https://skminibank.onrender.com/admin/withdraw.jsp")
            startActivity(intent)
        }
        findViewById<View>(R.id.nav_link_transfer)?.setOnClickListener { 
            startActivity(Intent(this, TransferActivity::class.java))
        }
        findViewById<View>(R.id.nav_link_fd)?.setOnClickListener { 
            val intent = Intent(this, WebPortalActivity::class.java)
            intent.putExtra("url", "https://skminibank.onrender.com/customer/fixed-deposit.jsp")
            startActivity(intent)
        }
        findViewById<View>(R.id.nav_link_loan)?.setOnClickListener { 
            val intent = Intent(this, WebPortalActivity::class.java)
            intent.putExtra("url", "https://skminibank.onrender.com/admin/loan-dashboard.jsp")
            startActivity(intent)
        }
        findViewById<View>(R.id.nav_link_kyc)?.setOnClickListener { 
            val intent = Intent(this, WebPortalActivity::class.java)
            intent.putExtra("url", "https://skminibank.onrender.com/admin/kyc.jsp")
            startActivity(intent)
        }
        findViewById<View>(R.id.nav_link_notify)?.setOnClickListener { openWebPortal() }
        findViewById<View>(R.id.nav_link_reports)?.setOnClickListener { openWebPortal() }
        findViewById<View>(R.id.nav_link_logout)?.setOnClickListener { logout() }
        
        // KYC Portal Link from status bar
        findViewById<View>(R.id.tvKycStatus)?.setOnClickListener { 
            val intent = Intent(this, WebPortalActivity::class.java)
            intent.putExtra("url", "https://skminibank.onrender.com/admin/kyc.jsp")
            startActivity(intent)
        }

        // Summary Cards
        findViewById<View>(R.id.cardCustomers)?.setOnClickListener { openWebPortal() }
        findViewById<View>(R.id.cardAccounts)?.setOnClickListener { openWebPortal() }
        findViewById<View>(R.id.cardBalance)?.setOnClickListener { openWebPortal() }
        findViewById<View>(R.id.cardTransactions)?.setOnClickListener {
            startActivity(Intent(this, TransferActivity::class.java))
        }

        updateBalanceDisplay()
        updateKycStatusDisplay()
    }

    private fun logout() {
        session.setLoggedIn(false)
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun openWebPortal() {
        val intent = Intent(this, WebPortalActivity::class.java)
        intent.putExtra("url", getString(R.string.app_base_url))
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        updateBalanceDisplay()
        updateKycStatusDisplay()
    }

    private fun updateBalanceDisplay() {
        tvBalance.text = String.format(Locale.getDefault(), "₹ %.2f", session.getBalance())
    }

    private fun updateKycStatusDisplay() {
        val status = session.getKycStatus()
        tvKycStatus.text = "KYC Status: $status"
        
        if (status.equals("VERIFIED", ignoreCase = true)) {
            tvKycStatus.setTextColor(android.graphics.Color.parseColor("#198754"))
            tvKycStatus.setBackgroundColor(android.graphics.Color.parseColor("#1000FF00"))
        } else if (status.equals("REJECTED", ignoreCase = true)) {
            tvKycStatus.setTextColor(android.graphics.Color.parseColor("#dc3545"))
            tvKycStatus.setBackgroundColor(android.graphics.Color.parseColor("#10FF0000"))
        } else {
            tvKycStatus.setTextColor(android.graphics.Color.parseColor("#ffc107"))
            tvKycStatus.setBackgroundColor(android.graphics.Color.parseColor("#10FFFF00"))
        }
    }
}
