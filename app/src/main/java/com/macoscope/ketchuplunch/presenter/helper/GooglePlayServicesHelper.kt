package com.macoscope.ketchuplunch.presenter.helper

import android.content.Context
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

class GooglePlayServicesHelper {

    data class CheckAvaialabilityResult(val isAvailable: Boolean, val connectionStatusCode: Int)

    fun acquireServices(context: Context): CheckAvaialabilityResult {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(context)
        val isAvailable = !apiAvailability.isUserResolvableError(connectionStatusCode)
        return CheckAvaialabilityResult(isAvailable, connectionStatusCode)
    }

    fun isAvailable(context: Context): Boolean {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(context)
        return connectionStatusCode == ConnectionResult.SUCCESS
    }
}
