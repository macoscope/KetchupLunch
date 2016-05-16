package com.macoscope.ketchuplunch.model.repository

import android.content.Context
import android.content.SharedPreferences
import android.support.annotation.VisibleForTesting
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.util.ExponentialBackOff

class AccountRepository {

    private val accountPrefrencesName = "ACCOUNT_SHARED_PREFS"
    private val accountNamePrefKey = "ACCOUNT_PREF_KEY"
    private val scopes: List<String> = listOf("https://www.googleapis.com/auth/spreadsheets")

    private var credential: GoogleAccountCredential
    private val context: Context
    private val sharedPreferences: SharedPreferences

    constructor(context: Context){
        credential = GoogleAccountCredential.usingOAuth2(
                context.applicationContext,
                scopes).setBackOff(ExponentialBackOff());
        this.context = context
        this.sharedPreferences = context.getSharedPreferences(accountPrefrencesName, Context.MODE_PRIVATE)
        setCredentialSelectedAccountName(getAccountName())
    }

    @VisibleForTesting
    constructor(context: Context,
                googleAccountCredential: GoogleAccountCredential,
                sharedPreferences: SharedPreferences){
        credential = googleAccountCredential
        this.context = context
        this.sharedPreferences = sharedPreferences
        setCredentialSelectedAccountName(getAccountName())
    }

    fun getAccountName(): String {
        val account = sharedPreferences.getString(accountNamePrefKey, "")
        return account
    }

    fun getUserCredentials(): GoogleAccountCredential = credential

    fun isAccountNameDefined(): Boolean = getAccountName() != ""

    fun setAccountName(accountName: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(accountNamePrefKey, accountName)
        editor.apply()
    }

    private fun setCredentialSelectedAccountName(accountName: String) {
        credential.selectedAccountName = accountName
    }
}
