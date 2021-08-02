package com.syte.ai.android_sdk.events;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.enums.EventsTag;

import java.util.Arrays;
import java.util.List;

/**
 * Default Syte event
 */
public class EventOfferClick extends BaseSyteEvent {

    @SerializedName("sku")
    private final String mSku;

    @SerializedName("position")
    private final int mPosition;

    public EventOfferClick(
            String sku,
            int position,
            String syteUrlReferer) {
        super("fe_offer_click", syteUrlReferer, Arrays.asList(EventsTag.DISCOVERY_BUTTON, EventsTag.CAMERA));
        mPosition = position;
        mSku = sku;
    }

    @Override
    public String getRequestBodyString() {
        return new Gson().toJson(this, EventOfferClick.class);
    }

}
