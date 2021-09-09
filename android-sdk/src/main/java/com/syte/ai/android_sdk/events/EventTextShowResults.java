package com.syte.ai.android_sdk.events;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.enums.EventsTag;
import com.syte.ai.android_sdk.enums.TextSearchEventType;

/**
 * This event should be sent to Syte every time results for a specific text search are shown
 */
public class EventTextShowResults extends BaseSyteEvent {

    @SerializedName("query")
    private String mQuery;

    @SerializedName("type")
    private String mType;

    @SerializedName("exact_count")
    private int mExactCount;

    /**
     * @param query user search query
     * @param type see {@link TextSearchEventType}
     * @param exactCount The number of displayed results
     * @param pageName unique page name given by the app developer
     */
    public EventTextShowResults(String query,
                                TextSearchEventType type,
                                int exactCount,
                                String pageName) {
        super("fe_text_show_results", pageName, EventsTag.TEXT_SEARCH);
        mQuery = query;
        mType = type.getName();
        mExactCount = exactCount;
    }

    @Override
    public String getRequestBodyString() {
        return new Gson().toJson(this, EventTextShowResults.class);
    }

}
