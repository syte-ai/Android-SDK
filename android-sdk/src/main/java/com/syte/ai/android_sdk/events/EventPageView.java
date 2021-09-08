package com.syte.ai.android_sdk.events;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.enums.EventsTag;

/**
 * This event should be sent to Syte every time a user opens a new page (on the web) or visit an app screen on mobile apps
 */
public class EventPageView extends BaseSyteEvent {

    @SerializedName("sku")
    private final String mSKU;

    /**
     * @param SKU product ID
     * @param pageName unique page name given by the app developer
     */
    public EventPageView(
            String SKU,
            String pageName
    ) {
        super("fe_page_view", pageName, EventsTag.ECOMMERCE);
        mSKU = SKU;
    }

    /**
     * Getter for SKU
     * @return SKU
     */
    public String getSku() {
        return mSKU;
    }

    @Override
    public String getRequestBodyString() {
        return new Gson().toJson(this, EventPageView.class);
    }

}