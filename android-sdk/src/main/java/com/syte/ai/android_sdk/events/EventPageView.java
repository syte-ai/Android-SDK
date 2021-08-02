package com.syte.ai.android_sdk.events;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.enums.EventsTag;

/**
 * Default Syte event
 */
public class EventPageView extends BaseSyteEvent {

    @SerializedName("sku")
    private final String mSKU;

    public EventPageView(
            String SKU,
            String syteUrlReferer
    ) {
        super("fe_page_view", syteUrlReferer, EventsTag.ECOMMERCE);
        mSKU = SKU;
    }

    @Override
    public String getRequestBodyString() {
        return new Gson().toJson(this, EventPageView.class);
    }

}