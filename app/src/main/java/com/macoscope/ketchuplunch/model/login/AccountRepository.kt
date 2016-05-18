package com.macoscope.ketchuplunch.model.login

import android.content.Context
import android.content.SharedPreferences
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential

class AccountRepository {

    private val accountNamePrefKey = "ACCOUNT_PREF_KEY"

    private var credentialWrapper: GoogleCredentialWrapper
    private val context: Context
    private val sharedPreferences: SharedPreferences

    constructor(context: Context,
                credential: GoogleCredentialWrapper,
                sharedPreferences: SharedPreferences){
        this.credentialWrapper = credential
        this.context = context
        this.sharedPreferences = sharedPreferences
        setCredentialSelectedAccountName(getAccountName())
    }

    fun getAccountName(): String {
        val account = sharedPreferences.getString(accountNamePrefKey, "")
        return account
    }

    fun getUserCredentials(): GoogleAccountCredential = credentialWrapper.credential

    fun isAccountNameDefined(): Boolean = getAccountName() != ""

    fun setAccountName(accountName: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(accountNamePrefKey, accountName)
        editor.apply()
    }

    private fun setCredentialSelectedAccountName(accountName: String) {
        credentialWrapper.setSelectedAccountName(accountName)
    }


}
