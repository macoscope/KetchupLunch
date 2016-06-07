package com.macoscope.ketchuplunch.presenter

import android.accounts.AccountManager
import android.app.Activity
import android.content.Intent
import com.macoscope.ketchuplunch.model.GooglePlayServices
import com.macoscope.ketchuplunch.model.NetworkAvailability
import com.macoscope.ketchuplunch.model.login.AccountRepository
import com.macoscope.ketchuplunch.model.login.GoogleCredentialWrapper
import com.macoscope.ketchuplunch.view.login.LoginView
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.lang.kotlin.deferredObservable
import rx.schedulers.Schedulers

class LoginPresenter(val loginView: LoginView,
                     val accountRepository: AccountRepository,
                     val googlePlayServices: GooglePlayServices,
                     val networkAvailability: NetworkAvailability,
                     val googleCredentialsWrapper: GoogleCredentialWrapper) {


    private var REQUEST_ACCOUNT_PICKER = 1000
    private var REQUEST_AUTHORIZATION = 1001
    private var REQUEST_GOOGLE_PLAY_SERVICES = 1002

    fun onCreate() {
        deferredObservable {
            Observable.just(isGooglePlayServicesAvailable())
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    when {
                        !it -> acquireGooglePlayServices()
                        !accountRepository.isAccountNameDefined() -> chooseAccount()
                        !isDeviceOnline() -> displayNoNetworkMessage()
                        else -> openMealsScreen()
                    }
                }
    }

    private fun displayNoNetworkMessage() {
        loginView.showNoNetworkMessage()
    }

    private fun openMealsScreen() {
        loginView.startLunchActivity()
    }

    private fun chooseAccount() {
        if (loginView.hasAccountPermissions()) {
            permissionGranted()
        } else {
            loginView.requestAccountPermissions()
        }
    }

    private fun isGooglePlayServicesAvailable(): Boolean = googlePlayServices.isAvailable()


    private fun acquireGooglePlayServices() {
        val (isAvailable, connectionStatusCode) = googlePlayServices.acquireServices()
        if (!isAvailable) {
            loginView.showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode, REQUEST_GOOGLE_PLAY_SERVICES)
        }
    }

    private fun isDeviceOnline(): Boolean = networkAvailability.isDeviceOnline()

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
        loginView.openSelectAccountDialog(googleCredentialsWrapper.userCredential, REQUEST_ACCOUNT_PICKER)
    }
}
