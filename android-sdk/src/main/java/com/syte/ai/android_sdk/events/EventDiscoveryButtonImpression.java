package com.syte.ai.android_sdk.events;

import com.syte.ai.android_sdk.enums.EventsTag;

public class EventDiscoveryButtonImpression extends BaseSyteEvent {

    public EventDiscoveryButtonImpression(String syteUrlReferer) {
        super("fe_discovery_button_impression", syteUrlReferer, EventsTag.DISCOVERY_BUTTON);
    }

    @Override
    public String getRequestBodyString() {
        return null;
    }

}
