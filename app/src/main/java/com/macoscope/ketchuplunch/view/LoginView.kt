package com.macoscope.ketchuplunch.view

interface LoginView {
    fun showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode: Int)
    fun showNoNetworkMessage()
}
