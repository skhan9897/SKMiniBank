package com.bank.userapp

import android.content.Context
import android.content.SharedPreferences

class UserSession(private val context: Context) {
    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences("BankPrefs", Context.MODE_PRIVATE)
    }

    fun setLoggedIn(loggedIn: Boolean) {
        prefs.edit().putBoolean("is_logged_in", loggedIn).apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean("is_logged_in", false)
    }

    fun getBalance(): Double {
        return prefs.getFloat("balance", 5000.0f).toDouble()
    }

    fun getCustomerId(): Int {
        return prefs.getInt("customer_id", 1)
    }

    fun setCustomerId(id: Int) {
        prefs.edit().putInt("customer_id", id).apply()
    }

    fun getKycStatus(): String {
        return prefs.getString("kyc_status", "PENDING") ?: "PENDING"
    }

    fun setKycStatus(status: String) {
        prefs.edit().putString("kyc_status", status).apply()
    }

    fun updateBalance(amount: Double) {
        val current = getBalance()
        val newBalance = (current - amount).toFloat()
        prefs.edit().putFloat("balance", newBalance).apply()
    }
}
