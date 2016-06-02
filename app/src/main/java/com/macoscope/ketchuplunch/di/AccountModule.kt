package com.macoscope.ketchuplunch.di

import android.content.Context
import android.content.SharedPreferences
import com.macoscope.ketchuplunch.model.login.AccountPreferencesFactory
import com.macoscope.ketchuplunch.model.login.AccountRepository
import com.macoscope.ketchuplunch.model.login.GoogleCredentialWrapper

class AccountModule(val context: Context) {

    companion object {
        var accountRepository: AccountRepository? = null
    }

    fun provideAccountRepository(): AccountRepository {
        return accountRepository ?: accountRepository()
    }

    private fun accountRepository(): AccountRepository {
        val credential = provideGoogleCredentialWrapper()
        val accountRepository = AccountRepository(context, credential, provideAccountPreferences())
        credential.setSelectedAccountName(accountRepository.getAccountName())
        return accountRepository
    }

    fun provideGoogleCredentialWrapper(): GoogleCredentialWrapper {
        return GoogleCredentialWrapper(context)
    }

    fun provideAccountPreferences(): SharedPreferences{
        return AccountPreferencesFactory(context).getPreferences()
    }
}