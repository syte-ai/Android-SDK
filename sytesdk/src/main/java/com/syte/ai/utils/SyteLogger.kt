package com.syte.ai.utils

import android.util.Log

/**
 * Created by Syte on 4/7/2019.
 *
 * A utility object to log information onto the console.
 */
object SyteLogger {

    /**
     * A boolean flag to enable/disable logging.
     */
    var isEnabled = true

    fun logNetworkUnavailable(message: String) {
        info("$message - Network is unavailable")
    }

    fun log(message: String) {
        info(message)
    }

    fun logStart(url: String) {
        info("FETCH STARTED: $url")
    }

    fun logSuccess(response: String) {
        info("FETCH SUCCEEDED: $response")
    }

    fun logFailure(error: String) {
        info("FETCH FAILED: $error")
    }

    private fun info(message: String) {
        if (isEnabled)
            Log.d("Syte", message)
    }
}