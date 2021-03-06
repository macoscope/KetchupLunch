package com.macoscope.ketchuplunch.model.login

import android.content.Context
import android.content.SharedPreferences

open class AccountRepository {

    private val accountNamePrefKey = "ACCOUNT_PREF_KEY"

    private val context: Context
    private val sharedPreferences: SharedPreferences

    constructor(context: Context,
                sharedPreferences: SharedPreferences){
        this.context = context
        this.sharedPreferences = sharedPreferences
    }

    open fun getAccountName(): String {
        val account = sharedPreferences.getString(accountNamePrefKey, "")
        return account
    }

    open fun isAccountNameDefined(): Boolean = getAccountName() != ""

    open fun setAccountName(accountName: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(accountNamePrefKey, accountName)
        editor.apply()
    }

    open fun clearAccount() {
        sharedPreferences.edit().remove(accountNamePrefKey).apply()
    }
}
