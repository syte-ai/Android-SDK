package com.syte.ai.android_sdk.data.result;

import androidx.annotation.Nullable;

/**
 * Generic wrapper for the API result.
 */
public class SyteResult<T> {

    /**
     * Data returned by the API.
     */
    @Nullable public T data = null;
    /**
     * Response result code.
     */
    public int resultCode = -1;
    /**
     * Indicates whether the response is successful.
     */
    public boolean isSuccessful = false;
    /**
     * Holds the error message if there is any.
     */
    @Nullable public String errorMessage = null;

    /**
     * Holds the exception if there is any.
     */
    @Nullable public Throwable exception = null;

}