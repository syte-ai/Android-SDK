package com.syte.ai.android_sdk;

import com.syte.ai.android_sdk.data.result.SyteResult;

public interface SyteCallback<T> {

    void onResult(SyteResult<T> syteResult);

}
