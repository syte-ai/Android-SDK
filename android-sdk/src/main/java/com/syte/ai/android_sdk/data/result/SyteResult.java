package com.syte.ai.android_sdk.data.result;

import androidx.annotation.Nullable;

public class SyteResult<T> {

    @Nullable public T data;
    public int resultCode;
    public boolean isSuccessful;

}