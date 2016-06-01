package com.macoscope.ketchuplunch.di

import android.content.Context
import com.macoscope.ketchuplunch.model.login.AccountPreferencesFactory
import com.macoscope.ketchuplunch.model.login.AccountRepository
import com.macoscope.ketchuplunch.model.login.GoogleCredentialWrapper

class AccountModule(val context: Context) {

    fun provideAccountRepository(): AccountRepository {
        return AccountRepository(context, GoogleCredentialWrapper(context), AccountPreferencesFactory(context).getPreferences())
    }
}