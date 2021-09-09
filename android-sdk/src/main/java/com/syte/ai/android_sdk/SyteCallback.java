package com.syte.ai.android_sdk;

import com.syte.ai.android_sdk.data.result.SyteResult;

/**
 * Generic callback used in asynchronous calls.
 * @param <T> type to return
 */
public interface SyteCallback<T> {

    /**
     * This method is called when the request result is available.
     * It is ALWAYS called on the UI thread.
     * @param syteResult {@link SyteResult}
     */
    void onResult(SyteResult<T> syteResult);

}
