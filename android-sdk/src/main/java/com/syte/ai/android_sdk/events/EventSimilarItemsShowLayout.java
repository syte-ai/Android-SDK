package com.syte.ai.android_sdk.events;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.enums.EventsTag;

public class EventSimilarItemsShowLayout extends BaseSyteEvent {

    @SerializedName("results_count")
    private final int mResultsCount;

    public EventSimilarItemsShowLayout(
            int resultsCount,
            String syteUrlReferer) {
        super("fe_si_show_layout", syteUrlReferer, EventsTag.SIMILAR_ITEMS);
        mResultsCount = resultsCount;
    }

    @Override
    public String getRequestBodyString() {
        return new Gson().toJson(this, EventSimilarItemsShowLayout.class);
    }

}
