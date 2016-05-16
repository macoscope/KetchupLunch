package com.macoscope.ketchuplunch.view

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential

interface LoginView {
    fun showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode: Int, requestCode: Int)
    fun showNoNetworkMessage()
    fun openSelectAccountDialog(googleAccountCredential: GoogleAccountCredential, requestCode: Int)
    fun showNoGooglePlayServices()
    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray)
    fun chooseAccount(userCredential: GoogleAccountCredential, requestCode: Int)
}
