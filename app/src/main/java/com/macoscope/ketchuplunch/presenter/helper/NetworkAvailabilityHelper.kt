package com.macoscope.ketchuplunch.presenter.helper

import android.content.Context
import android.net.ConnectivityManager

class NetworkAvailabilityHelper {

    fun isDeviceOnline(context: Context): Boolean {
        val connectionManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectionManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
