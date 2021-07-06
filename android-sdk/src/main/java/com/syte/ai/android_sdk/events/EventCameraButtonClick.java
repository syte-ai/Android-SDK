package com.syte.ai.android_sdk.events;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.enums.EventsTag;
import com.syte.ai.android_sdk.enums.Placement;

public class EventCameraButtonClick extends BaseSyteEvent{

    @SerializedName("placement")
    private String mPlacement;

    public EventCameraButtonClick(
            Placement placement,
            String syteUrlReferer
    ) {
        super("fe_camera_button_click", syteUrlReferer, EventsTag.CAMERA);
        mPlacement = placement.getName();
    }

    @Override
    public String getRequestBodyString() {
        return new Gson().toJson(this, EventCameraButtonClick.class);
    }

}