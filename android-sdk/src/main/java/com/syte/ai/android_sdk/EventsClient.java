package com.syte.ai.android_sdk;

import com.syte.ai.android_sdk.data.SyteEvent;
import com.syte.ai.android_sdk.data.result.SyteResult;

public interface EventsClient {

    SyteResult launch(SyteEvent requestData);

    void launchAsync(SyteEvent requestData, SyteCallback callback);

}
