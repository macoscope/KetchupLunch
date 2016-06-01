package com.macoscope.ketchuplunch.model.login

import android.content.Context
import android.content.SharedPreferences

class AccountPreferencesFactory(val context: Context) {
    private val accountPreferencesName = "ACCOUNT_SHARED_PREFS"

    fun getPreferences(): SharedPreferences = context.getSharedPreferences(accountPreferencesName, Context.MODE_PRIVATE)
}
