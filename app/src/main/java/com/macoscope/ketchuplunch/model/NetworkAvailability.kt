package com.macoscope.ketchuplunch.model

import android.content.Context
import android.net.ConnectivityManager

class NetworkAvailability(val context: Context) {

    fun isDeviceOnline(): Boolean {
        val connectionManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectionManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
