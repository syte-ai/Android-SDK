package com.syte.ai.android_sdk;

import com.syte.ai.android_sdk.data.SyteEvent;
import com.syte.ai.android_sdk.data.result.SyteResult;

public interface EventsClient {

    //TODO: proper use of generics
    SyteResult launch(SyteEvent requestData);

    //TODO: proper use of generics
    void launchAsync(SyteEvent requestData, SyteCallback callback);

}
