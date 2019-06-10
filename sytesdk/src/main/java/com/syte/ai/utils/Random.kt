package com.syte.ai.utils

import java.util.*

/**
 * Created by Syte on 4/11/2019.
 */
private const val ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm"

/**
 * Generates a random string with a specific length.
 *
 * @param sizeOfRandomString the length of the random string to be generated.
 * @return a random string with the requested length.
 */
fun generateSessionId(sizeOfRandomString: Int): String {
    val random = Random()
    val sb = StringBuilder(sizeOfRandomString)
    for (i in 0 until sizeOfRandomString)
        sb.append(ALLOWED_CHARACTERS[random.nextInt(ALLOWED_CHARACTERS.length)])
    return sb.toString()
}