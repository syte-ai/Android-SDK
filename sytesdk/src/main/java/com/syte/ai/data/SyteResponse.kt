package com.syte.ai.data

/**
 * Created by Syte on 4/8/2019.
 *
 * This class serves as a wrapper around a response that was retrieved from the
 * remote Syte API.
 */
data class SyteResponse<T>(
    /**
     * The original HTTP response body that was retrieved after an API call.
     */
    var data: T? = null,
    /**
     * The HTTP response code associated with the API call that was made.
     */
    var status: Int? = null,
    /**
     * A brief description of the status code.
     */
    var statusDescription: String = ""
) {
    companion object {
        const val NETWORK_UNAVAILABLE = "Network is unavailable"

        fun <T> error503Response(statusDescription: String): SyteResponse<T> {
            return SyteResponse(status = 503, statusDescription = statusDescription)
        }

        fun <T> errorResponse(status: Int?, statusDescription: String): SyteResponse<T> {
            return SyteResponse(status = status, statusDescription = statusDescription)
        }

        fun <T> successResponse(data: T, status: Int?, statusDescription: String = "SUCCESS"): SyteResponse<T> {
            return SyteResponse(data, status, statusDescription)
        }

        fun <T> noNetworkConnectionResponse(): SyteResponse<T> {
            return SyteResponse(
                status = 503,
                statusDescription = NETWORK_UNAVAILABLE
            )
        }
    }
}