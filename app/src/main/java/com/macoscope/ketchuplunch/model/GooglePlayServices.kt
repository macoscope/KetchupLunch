package com.macoscope.ketchuplunch.model

import android.content.Context
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

class GooglePlayServices(val context: Context) {

    data class CheckAvaialabilityResult(val isAvailable: Boolean, val connectionStatusCode: Int)

    fun acquireServices(): CheckAvaialabilityResult {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(context)
        val isAvailable = !apiAvailability.isUserResolvableError(connectionStatusCode)
        return CheckAvaialabilityResult(isAvailable, connectionStatusCode)
    }

    fun isAvailable(): Boolean {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(context)
        return connectionStatusCode == ConnectionResult.SUCCESS
    }
}
