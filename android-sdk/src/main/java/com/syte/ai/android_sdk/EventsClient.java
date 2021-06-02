package com.syte.ai.android_sdk;

import com.syte.ai.android_sdk.data.EventRequestData;
import com.syte.ai.android_sdk.data.result.SyteResult;

public interface EventsClient {

    SyteResult launch(EventRequestData requestData);

    void launchAsync(EventRequestData requestData, SyteCallback callback);

}
