package com.syte.ai.android_sdk.events;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.enums.EventsTag;

/**
 * Default Syte event
 */
public class EventShopTheLookShowLayout extends BaseSyteEvent {

    @SerializedName("results_count")
    private final int mResultsCount;

    public EventShopTheLookShowLayout(
            int resultsCount,
            String syteUrlReferer
    ) {
        super("fe_stl_show_layout", syteUrlReferer, EventsTag.SHOP_THE_LOOK);
        mResultsCount = resultsCount;
    }

    @Override
    public String getRequestBodyString() {
        return new Gson().toJson(this, EventShopTheLookShowLayout.class);
    }

}
