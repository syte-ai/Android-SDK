package com.syte.ai.android_sdk.events;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.enums.EventsTag;
import com.syte.ai.android_sdk.enums.Placement;

/**
 * This event should be sent to Syte every time a user clicks on discovery button
 */
public class EventDiscoveryButtonClick extends BaseSyteEvent {

    @SerializedName("imageSrc")
    private String mImageSrc;

    @SerializedName("placement")
    private String mPlacement;

    /**
     * @param imageSrc the path to the image that discovery button refers to
     * @param placement see {@link Placement}
     * @param pageName unique page name given by the app developer
     */
    public EventDiscoveryButtonClick(
            String imageSrc,
            String placement,
            String pageName) {
        super("fe_discovery_button_click", pageName, EventsTag.DISCOVERY_BUTTON);
        mImageSrc = imageSrc;
        mPlacement = placement;
    }

    @Override
    public String getRequestBodyString() {
        return new Gson().toJson(this, EventDiscoveryButtonClick.class);
    }

}
