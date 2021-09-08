package com.syte.ai.android_sdk.events;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.enums.EventsTag;

/**
 * This event should be sent to Syte every time a shop the look carousel is visible
 */
public class EventShopTheLookShowLayout extends BaseSyteEvent {

    @SerializedName("results_count")
    private final int mResultsCount;

    /**
     *
     * @param resultsCount The number of displayed results
     * @param pageName unique page name given by the app developer
     */
    public EventShopTheLookShowLayout(
            int resultsCount,
            String pageName
    ) {
        super("fe_stl_show_layout", pageName, EventsTag.SHOP_THE_LOOK);
        mResultsCount = resultsCount;
    }

    @Override
    public String getRequestBodyString() {
        return new Gson().toJson(this, EventShopTheLookShowLayout.class);
    }

}
