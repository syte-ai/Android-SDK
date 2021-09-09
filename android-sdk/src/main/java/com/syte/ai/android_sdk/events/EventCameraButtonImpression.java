package com.syte.ai.android_sdk.events;

import com.syte.ai.android_sdk.enums.EventsTag;

/**
 * This event should be sent to Syte every time camera button becomes visible to a user / enters user viewport.
 */
public class EventCameraButtonImpression extends BaseSyteEvent {

    /**
     * @param pageName unique page name given by the app developer
     */
    public EventCameraButtonImpression(String pageName) {
        super("fe_camera_button_impression", pageName, EventsTag.CAMERA);
    }

    @Override
    public String getRequestBodyString() {
        return null;
    }

}
