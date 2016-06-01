package com.macoscope.ketchuplunch.di

import android.content.Context
import com.macoscope.ketchuplunch.model.GooglePlayServices
import com.macoscope.ketchuplunch.model.NetworkAvailability
import com.macoscope.ketchuplunch.model.login.AccountPermission
import com.macoscope.ketchuplunch.presenter.LoginPresenter
import com.macoscope.ketchuplunch.view.login.LoginView

class LoginModule(val accountModule: AccountModule, val context: Context, val loginView: LoginView) {

    companion object {
        var accountPermission: AccountPermission? = null
    }

    fun provideAccountPermission(): AccountPermission {
        return accountPermission ?: AccountPermission()
    }

    fun provideLoginPresenter(): LoginPresenter {
        return LoginPresenter(loginView,
                accountModule.provideAccountRepository(),
                GooglePlayServices(context),
                NetworkAvailability(context),
                accountModule.provideGoogleCredentialWrapper())
    }
}