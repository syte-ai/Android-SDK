package com.syte.ai.exceptions

/**
 * Created by Syte on 4/8/2019.
 */
class InvalidGenderException(gender: String) :
    Exception("An invalid gender '$gender' was specified.")