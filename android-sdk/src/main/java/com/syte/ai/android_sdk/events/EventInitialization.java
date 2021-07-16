package com.syte.ai.android_sdk.events;

import com.syte.ai.android_sdk.enums.EventsTag;

public class EventInitialization extends BaseSyteEvent {

    public EventInitialization() {
        super("syte_init", "syte_android_sdk", EventsTag.SYTE_ANDROID_SDK);
    }

    @Override
    public String getRequestBodyString() {
        return null;
    }

}
