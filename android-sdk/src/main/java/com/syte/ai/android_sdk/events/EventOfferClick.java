package com.syte.ai.android_sdk.events;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.enums.EventsTag;

import java.util.Arrays;
import java.util.List;

/**
 * This event should be sent to Syte every time a user clicks on product in Camera results
 */
public class EventOfferClick extends BaseSyteEvent {

    @SerializedName("sku")
    private final String mSku;

    @SerializedName("position")
    private final int mPosition;

    /**
     * @param sku Unique identifier of product sent to Syte before
     * @param position index of the result in the results list
     * @param pageName unique page name given by the app developer
     */
    public EventOfferClick(
            String sku,
            int position,
            String pageName) {
        super("fe_offer_click", pageName, Arrays.asList(EventsTag.DISCOVERY_BUTTON, EventsTag.CAMERA));
        mPosition = position;
        mSku = sku;
    }

    @Override
    public String getRequestBodyString() {
        return new Gson().toJson(this, EventOfferClick.class);
    }

}
