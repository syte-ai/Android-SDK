package com.syte.ai.android_sdk.events;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.enums.EventsTag;

/**
 * Default Syte event
 */
public class EventSimilarItemsOfferClick extends BaseSyteEvent {

    @SerializedName("sku")
    private final String mSku;

    @SerializedName("position")
    private final int mPosition;

    public EventSimilarItemsOfferClick(
            String sku,
            int position,
            String syteUrlReferer) {
        super("fe_offer_click", syteUrlReferer, EventsTag.SIMILAR_ITEMS);
        mPosition = position;
        mSku = sku;
    }

    @Override
    public String getRequestBodyString() {
        return new Gson().toJson(this, EventSimilarItemsOfferClick.class);
    }

}
