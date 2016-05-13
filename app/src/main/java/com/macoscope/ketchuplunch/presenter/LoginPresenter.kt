package com.macoscope.ketchuplunch.presenter

import android.content.Context
import android.net.ConnectivityManager
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.macoscope.ketchuplunch.model.AccountRepository
import com.macoscope.ketchuplunch.view.LoginView


class LoginPresenter(val context: Context, val loginView: LoginView) {

    private val accountRepository: AccountRepository = AccountRepository(context)

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
        //TODO: implement
    }

    private fun chooseAccount() {
        //TODO: implement
    }

    private fun isGooglePlayServicesAvailable(): Boolean {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(context)
        return connectionStatusCode == ConnectionResult.SUCCESS
    }

    private fun acquireGooglePlayServices() {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(context)
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            loginView.showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode)
        }
    }

    private fun isDeviceOnline(): Boolean {
        val connectionMAnager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectionMAnager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

}
