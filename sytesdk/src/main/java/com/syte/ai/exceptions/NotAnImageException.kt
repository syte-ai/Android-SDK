package com.syte.ai.exceptions

/**
 * Created by Syte on 4/13/2019.
 */
class NotAnImageException(uri: String) :
    Exception("The URI '$uri' does not point to a valid image file.")