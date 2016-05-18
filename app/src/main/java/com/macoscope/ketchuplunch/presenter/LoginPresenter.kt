package com.macoscope.ketchuplunch.presenter

import android.accounts.AccountManager
import android.app.Activity
import android.content.Context
import android.content.Intent
import com.macoscope.ketchuplunch.model.GooglePlayServices
import com.macoscope.ketchuplunch.model.NetworkAvailability
import com.macoscope.ketchuplunch.model.login.AccountPreferencesFactory
import com.macoscope.ketchuplunch.model.login.AccountRepository
import com.macoscope.ketchuplunch.model.login.GoogleCredentialWrapper
import com.macoscope.ketchuplunch.view.LoginView

class LoginPresenter(val context: Context, val loginView: LoginView) {

    private val accountRepository: AccountRepository = AccountRepository(context,
            GoogleCredentialWrapper(context),
            AccountPreferencesFactory(context).getPreferences())
    private val REQUEST_ACCOUNT_PICKER = 1000
    private val REQUEST_AUTHORIZATION = 1001
    private val REQUEST_GOOGLE_PLAY_SERVICES = 1002

    fun onCreate() {
        when {
            !isGooglePlayServicesAvailable() -> acquireGooglePlayServices()
            !accountRepository.isAccountNameDefined() -> chooseAccount()
            !isDeviceOnline() -> displayNoNetworkMessage()
            else -> openMealsScreen()
        }
    }


    private fun displayNoNetworkMessage() {
        loginView.showNoNetworkMessage()
    }

    private fun openMealsScreen() {
        loginView.startLunchActivity()
    }

    private fun chooseAccount() {

        loginView.chooseAccount(accountRepository.getUserCredentials(), REQUEST_ACCOUNT_PICKER)
    }

    private fun isGooglePlayServicesAvailable(): Boolean = GooglePlayServices().isAvailable(context)


    private fun acquireGooglePlayServices() {
        val (isAvailable, connectionStatusCode) = GooglePlayServices().acquireServices(context)
        if (!isAvailable) {
            loginView.showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode, REQUEST_GOOGLE_PLAY_SERVICES)
        }
    }

    private fun isDeviceOnline(): Boolean = NetworkAvailability().isDeviceOnline(context)


    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_GOOGLE_PLAY_SERVICES -> resultGooglePlayServicesRequest(resultCode)
            REQUEST_ACCOUNT_PICKER -> storeAccountName(resultCode, data)
            REQUEST_AUTHORIZATION -> {
                if (resultCode == Activity.RESULT_OK) {
                    openMealsScreen()
                }
            }
        }
    }

    private fun storeAccountName(resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK
                && data != null
                && data.extras != null) {
            val accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME)
            if (accountName != null) {
                accountRepository.setAccountName(accountName)
                openMealsScreen()
            }
        }
    }

    private fun resultGooglePlayServicesRequest(resultCode: Int) {
        if (resultCode != Activity.RESULT_OK) {
            loginView.showNoGooglePlayServices()
        } else {
            openMealsScreen()
        }
    }

    fun permissionDenied() {
        //We display choose account dialog until user grants permission
        chooseAccount()
    }

    fun permissionGranted() {
        loginView.openSelectAccountDialog(accountRepository.getUserCredentials(), REQUEST_ACCOUNT_PICKER)
    }


}
