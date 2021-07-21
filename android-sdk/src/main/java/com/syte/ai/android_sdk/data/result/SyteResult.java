package com.syte.ai.android_sdk.data.result;

import androidx.annotation.Nullable;

public class SyteResult<T> {

    @Nullable public T data = null;
    public int resultCode = -1;
    public boolean isSuccessful = false;
    @Nullable public String errorMessage = null;

}