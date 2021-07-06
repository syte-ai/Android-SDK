package com.syte.ai.android_sdk.events;

import com.syte.ai.android_sdk.enums.EventsTag;

public class EventCameraButtonImpression extends BaseSyteEvent {

    public EventCameraButtonImpression(String syteUrlReferer) {
        super("fe_camera_button_impression", syteUrlReferer, EventsTag.CAMERA);
    }

    @Override
    public String getRequestBodyString() {
        return null;
    }

}
