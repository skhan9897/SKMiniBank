package com.bank.userapp

import android.content.Context
import android.content.SharedPreferences

class UserSession(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("BankPrefs", Context.MODE_PRIVATE)

    fun setLoggedIn(loggedIn: Boolean) {
        prefs.edit().putBoolean("is_logged_in", loggedIn).apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean("is_logged_in", false)
    }

    fun getBalance(): Double {
        return prefs.getFloat("balance", 5000.0f).toDouble()
    }

    fun updateBalance(amount: Double) {
        val current = getBalance()
        prefs.edit().putFloat("balance", (current - amount).toFloat()).apply()
    }
}
