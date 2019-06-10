package com.syte.ai.network

/**
 * Created by Syte on 4/7/2019.
 */

/**
 * For test purposes.
 *
 * A configurable network monitor that reports on the current network status.
 * Used to simulate network changes when running unit tests.
 */
class TestNetworkMonitor(var isConnected: Boolean = true) : NetworkMonitor {
    override fun isNetworkAvailable() = isConnected
}