package com.syte.ai.android_sdk.events;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.enums.EventsTag;
import com.syte.ai.android_sdk.enums.TextSearchEventType;

public class EventTextShowResults extends BaseSyteEvent {

    @SerializedName("query")
    private String mQuery;

    @SerializedName("type")
    private String mType;

    @SerializedName("exact_count")
    private int mExactCount;

    public EventTextShowResults(String query,
                                TextSearchEventType type,
                                int exactCount,
                                String syteUrlReferer) {
        super("fe_text_show_results", syteUrlReferer, EventsTag.TEXT_SEARCH);
        mQuery = query;
        mType = type.getName();
        mExactCount = exactCount;
    }

    @Override
    public String getRequestBodyString() {
        return new Gson().toJson(this, EventTextShowResults.class);
    }

}
