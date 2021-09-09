package com.syte.ai.android_sdk.events;

import com.syte.ai.android_sdk.enums.EventsTag;

/**
 * This event should be sent to Syte every time discovery button becomes visible to a user / enters user viewport.
 */
public class EventDiscoveryButtonImpression extends BaseSyteEvent {

    /**
     * @param pageName unique page name given by the app developer
     */
    public EventDiscoveryButtonImpression(String pageName) {
        super("fe_discovery_button_impression", pageName, EventsTag.DISCOVERY_BUTTON);
    }

    @Override
    public String getRequestBodyString() {
        return null;
    }

}
