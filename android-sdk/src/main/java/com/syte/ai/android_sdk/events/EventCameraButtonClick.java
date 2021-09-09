package com.syte.ai.android_sdk.events;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.enums.EventsTag;
import com.syte.ai.android_sdk.enums.Placement;

/**
 * This event should be sent to Syte every time a user clicks on camera button
 */
public class EventCameraButtonClick extends BaseSyteEvent{

    @SerializedName("placement")
    private String mPlacement;

    /**
     * @param placement see {@link Placement}
     * @param pageName unique page name given by the app developer
     */
    public EventCameraButtonClick(
            String placement,
            String pageName
    ) {
        super("fe_camera_button_click", pageName, EventsTag.CAMERA);
        mPlacement = placement;
    }

    @Override
    public String getRequestBodyString() {
        return new Gson().toJson(this, EventCameraButtonClick.class);
    }

}
