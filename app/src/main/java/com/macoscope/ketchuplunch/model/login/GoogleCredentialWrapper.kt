package com.macoscope.ketchuplunch.model.login

import android.content.Context
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.util.ExponentialBackOff

open class GoogleCredentialWrapper(val context: Context) {

    private val scopes: List<String> = listOf("https://www.googleapis.com/auth/spreadsheets")

    val userCredential: GoogleAccountCredential = GoogleAccountCredential.usingOAuth2(
            context.applicationContext,
            scopes).setBackOff(ExponentialBackOff());

    open fun setSelectedAccountName(accountName: String) {
        userCredential.selectedAccountName = accountName
    }

    open fun clearSelectedAccountName(){
        userCredential.selectedAccountName = null
    }
}
