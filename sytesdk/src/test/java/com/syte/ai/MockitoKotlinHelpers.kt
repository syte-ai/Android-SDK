package com.syte.ai

import org.mockito.ArgumentCaptor

/**
 * Created by Syte on 4/7/2019.
 */

fun <T> capture(argumentCaptor: ArgumentCaptor<T>): T = argumentCaptor.capture()