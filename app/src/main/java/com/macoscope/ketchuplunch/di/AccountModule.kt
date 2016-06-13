package com.macoscope.ketchuplunch.di

import android.content.Context
import android.content.SharedPreferences
import com.macoscope.ketchuplunch.model.login.AccountPreferencesFactory
import com.macoscope.ketchuplunch.model.login.AccountRepository
import com.macoscope.ketchuplunch.model.login.GoogleCredentialWrapper

open class AccountModule(val context: Context) {

    companion object {
        var accountRepository: AccountRepository? = null
    }

    fun provideAccountRepository(): AccountRepository {
        return accountRepository ?: accountRepository()
    }

    private fun accountRepository(): AccountRepository {
        return AccountRepository(context, provideAccountPreferences())
    }

    fun provideGoogleCredentialWrapper(): GoogleCredentialWrapper {
        val credential = GoogleCredentialWrapper(context)
        credential.setSelectedAccountName(provideAccountRepository().getAccountName())
        return credential
    }

    fun provideAccountPreferences(): SharedPreferences{
        return AccountPreferencesFactory(context).getPreferences()
    }
}