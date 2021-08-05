package com.syte.ai.android_sdk.events;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.enums.EventsTag;
import com.syte.ai.android_sdk.enums.Placement;

/**
 * Default Syte event
 */
public class EventDiscoveryButtonClick extends BaseSyteEvent {

    @SerializedName("imageSrc")
    private String mImageSrc;

    @SerializedName("placement")
    private Placement mPlacement;

    public EventDiscoveryButtonClick(
            String imageSrc,
            Placement placement,
            String syteUrlReferer) {
        super("fe_discovery_button_click", syteUrlReferer, EventsTag.DISCOVERY_BUTTON);
        mImageSrc = imageSrc;
        mPlacement = placement;
    }

    @Override
    public String getRequestBodyString() {
        return new Gson().toJson(this, EventDiscoveryButtonClick.class);
    }

}
