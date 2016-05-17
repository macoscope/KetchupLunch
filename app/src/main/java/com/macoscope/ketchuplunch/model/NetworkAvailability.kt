package com.macoscope.ketchuplunch.model

import android.content.Context
import android.net.ConnectivityManager

class NetworkAvailability {

    fun isDeviceOnline(context: Context): Boolean {
        val connectionManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectionManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
