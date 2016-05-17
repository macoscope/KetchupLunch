package com.macoscope.ketchuplunch.model.login

import android.content.Context
import android.content.SharedPreferences

class AccountPreferencesFactory(val context: Context) {
    private val accountPrefrencesName = "ACCOUNT_SHARED_PREFS"

    fun getPreferences(): SharedPreferences = context.getSharedPreferences(accountPrefrencesName, Context.MODE_PRIVATE)
}
