package com.syte.ai.network

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by Syte on 4/7/2019.
 *
 * A network monitor that reports on the current network status.
 */
interface NetworkMonitor {
    /**
     * Returns true if a network is available, false otherwise.
     */
    fun isNetworkAvailable(): Boolean
}

/**
 * A network monitor that reports on the current network status. It uses the
 * Android system's connectivity manager to find out.
 */
class AndroidNetworkMonitor(var context: Context) : NetworkMonitor {
    /**
     * Returns true if the Android device is connected to a network, false otherwise.
     */
    override fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}