package com.syte.ai.exceptions

/**
 * Created by Syte on 4/7/2019.
 */
class SyteInitializationException(message: String, var code: Int? = null) : Exception(message) {
    override val message: String
        get() = super.message + if (code != null) " - with an error code of $code" else ""
}