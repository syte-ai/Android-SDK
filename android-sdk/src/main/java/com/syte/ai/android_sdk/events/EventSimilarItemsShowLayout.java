package com.syte.ai.android_sdk.events;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.enums.EventsTag;

/**
 * This event should be sent to Syte every time a similar items carousel is visible
 */
public class EventSimilarItemsShowLayout extends BaseSyteEvent {

    @SerializedName("results_count")
    private final int mResultsCount;

    /**
     * @param resultsCount The number of displayed results
     * @param pageName unique page name given by the app developer
     */
    public EventSimilarItemsShowLayout(
            int resultsCount,
            String pageName) {
        super("fe_si_show_layout", pageName, EventsTag.SIMILAR_ITEMS);
        mResultsCount = resultsCount;
    }

    @Override
    public String getRequestBodyString() {
        return new Gson().toJson(this, EventSimilarItemsShowLayout.class);
    }

}
