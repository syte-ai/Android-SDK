package com.syte.ai.exceptions

/**
 * Created by Syte on 4/8/2019.
 */
class InvalidCurrencyException(currency: String) :
    Exception("Invalid currency '$currency'. A 3-letters currency is required.")