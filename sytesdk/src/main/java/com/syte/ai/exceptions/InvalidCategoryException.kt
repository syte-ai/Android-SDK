package com.syte.ai.exceptions

/**
 * Created by Syte on 4/8/2019.
 */
class InvalidCategoryException(category: String) :
    Exception("An invalid category '$category' was specified.")